package com.example.masteringpagingthree_2.data.repository // ktlint-disable package-name

import androidx.paging.Pager
import androidx.paging.PagingConfig
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
                pageSize = 29,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                getPagingSource(query)
            }
        ).flow

    private fun getPagingSource(query: String): UnsplashPagingSource {
        unsplashPagingSource = unsplashPagingSourceFactory.createPagingSource(query)
        return unsplashPagingSource
    }
}
