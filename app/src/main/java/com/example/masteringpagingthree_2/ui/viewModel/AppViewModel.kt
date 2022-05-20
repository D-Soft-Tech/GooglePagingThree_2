package com.example.masteringpagingthree_2.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.masteringpagingthree_2.data.repository.Repository
import com.example.masteringpagingthree_2.util.AppConstants.DEFAULT_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val photos = currentQuery.switchMap {
        repository.getSearchResults(it)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
}
