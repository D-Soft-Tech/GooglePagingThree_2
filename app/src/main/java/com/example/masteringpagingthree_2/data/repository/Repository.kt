package com.example.masteringpagingthree_2.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.masteringpagingthree_2.data.pagination.PagingSourceFactory
import com.example.masteringpagingthree_2.data.pagination.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor() {
    private lateinit var unsplashPagingSource: UnsplashPagingSource

    @Inject
    lateinit var unsplashPagingSourceFactory: PagingSourceFactory
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                getPagingSource(query)
            }
        ).liveData

    private fun getPagingSource(query: String): UnsplashPagingSource {
        unsplashPagingSource = unsplashPagingSourceFactory.createPagingSource(query)
        return unsplashPagingSource
    }
}
