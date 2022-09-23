package com.example.masteringpagingthree_2.util

import com.example.masteringpagingthree_2.BuildConfig

object AppConstants {
    const val CLIENT_ID = BuildConfig.CLIENT_ID_ACCESS_KEY
    const val BASE_URL = "https://api.unsplash.com/"
    const val UNSPLASH_STARTING_PAGE_INDEX = 1
    const val DEFAULT_QUERY = "cats"
    const val TAG_NOTIFICATION_RECEIVED = "TAG_NOTIFICATION_RECEIVED"
    const val TAG_NOTIFICATION_RECEIVED_2 = "TAG_NOTIFICATION_RECEIVED_2"
    const val TAG_NEW_TOKEN_RECEIVED = "TAG_NEW_TOKEN_RECEIVED"
    const val TAG_PROGRESS_LOADER = "TAG_PROGRESS_LOADER"
}
