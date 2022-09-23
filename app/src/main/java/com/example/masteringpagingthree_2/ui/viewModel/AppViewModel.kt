package com.example.masteringpagingthree_2.ui.viewModel // ktlint-disable package-name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.masteringpagingthree_2.data.localDb.UnsplashLocalDbDao
import com.example.masteringpagingthree_2.data.model.Resource
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.data.pagination.UnsplashPhotosRemoteMediator
import com.example.masteringpagingthree_2.data.repository.Repository
import com.example.masteringpagingthree_2.util.AppConstants.DEFAULT_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: Repository,
    remoteMediator: UnsplashPhotosRemoteMediator,
    private val localDbDao: UnsplashLocalDbDao
) : ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
//    val photos = currentQuery.switchMap {
//        repository.getSearchResults(it)
//            .cachedIn(viewModelScope) // cachedIn(ViewModelScope) helps to prevent the app from crashing when rotated,
//        // if we don't do this, our app will crash when we rotate the screen because we can not load from the same paging data twice
//        // This means when the app is rotated, the activity will be recreated and this value will want to be loaded again
//        // but we can't load from the same paging data again, hence the app will crash, this is why we have to cache the result
//        // in view model and this is where the new data will be loaded from when the activity is recreated
//    }

    val photos: LiveData<Resource<PagingData<UnsplashModel>?>> =
        currentQuery.distinctUntilChanged().switchMap { queryString ->
            liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(Resource.loading(null))
                try {
                    repository.getSearchResults(queryString).collect {
                        emit(Resource.success(it))
                    }
                } catch (e: Exception) {
                    emit(Resource.error(null))
                }
            }
        }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        PagingConfig(pageSize = 30),
        remoteMediator = remoteMediator
    ) {
        localDbDao.getAllPhotos()
    }.flow
}
