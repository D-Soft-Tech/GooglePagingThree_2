package com.example.masteringpagingthree_2.data.pagination

import dagger.assisted.AssistedFactory

@AssistedFactory
interface PagingSourceFactory {
    fun createPagingSource(
        query: String
    ): UnsplashPagingSource
}
