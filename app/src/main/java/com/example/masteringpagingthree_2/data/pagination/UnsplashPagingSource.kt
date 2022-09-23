package com.example.masteringpagingthree_2.data.pagination // ktlint-disable package-name

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.data.remote.apiService.ApiService
import com.example.masteringpagingthree_2.util.AppConstants.UNSPLASH_STARTING_PAGE_INDEX
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UnsplashPagingSource @AssistedInject constructor(
    @Assisted private val query: String
) : PagingSource<Int, UnsplashModel>() {
    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var gson: Gson

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashModel> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = apiService.getPhotos(query, position, params.loadSize)
            if (response.isSuccessful) {
                val photos = response.body()!!.results
                LoadResult.Page(
                    data = photos,
                    prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(Throwable(gson.toJson(response.errorBody()!!)))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashModel>): Int? {
        return state.anchorPosition
    }
}
