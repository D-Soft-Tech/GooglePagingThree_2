package com.example.masteringpagingthree_2.data.pagination // ktlint-disable package-name

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.masteringpagingthree_2.data.localDb.UnsplashLocalDbDao
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.data.model.UnsplashPhotoRemoteKey
import com.example.masteringpagingthree_2.data.remote.apiService.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class UnsplashPhotosRemoteMediator @Inject constructor(
    private val unsplashPhotoDao: UnsplashLocalDbDao,
    private val remoteApi: ApiService
) : RemoteMediator<Int, UnsplashModel>() {
    private val initialPge: Int = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashModel>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKey = getLastKey(state) ?: throw InvalidObjectException("Invalid")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPge
                }
            }
            val response = remoteApi.getPhotos("cats", page, state.config.pageSize)
            if (response.isSuccessful) {
                val endOfPagination = response.body()?.results?.size!! < state.config.pageSize
                response.body()?.results?.let {
                    if (loadType == LoadType.REFRESH) {
                        unsplashPhotoDao.deleteAllPhotos()
                        unsplashPhotoDao.deleteAllRemoteKeys()
                    }
                    val prev = if (page == initialPge) null else page - 1
                    val next = if (endOfPagination) null else page + 1
                    val listOfPhotoRemoteKey = response.body()?.results?.map { photo ->
                        UnsplashPhotoRemoteKey(photo.id, prev, next)
                    }
                    listOfPhotoRemoteKey?.let { remoteKeys ->
                        remoteKeys.forEach {
                            unsplashPhotoDao.insertAllRemoteKeys(it)
                        }
                    }
                    it.forEach { dataEntity ->
                        unsplashPhotoDao.insertListOfPhotos(dataEntity)
                    }
                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Error(Throwable("Error occurred"))
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getClosestKeys(state: PagingState<Int, UnsplashModel>): UnsplashPhotoRemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { photo ->
                unsplashPhotoDao.getAllRemoteKeys(photo.id)
            }
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, UnsplashModel>): UnsplashPhotoRemoteKey? {
        return state.lastItemOrNull()?.let {
            unsplashPhotoDao.getAllRemoteKeys(it.id)
        }
    }
}
