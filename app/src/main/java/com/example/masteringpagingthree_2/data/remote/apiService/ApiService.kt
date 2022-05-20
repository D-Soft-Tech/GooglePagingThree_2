package com.example.masteringpagingthree_2.data.remote.apiService

import com.example.masteringpagingthree_2.data.model.UnsplashDataModel
import com.example.masteringpagingthree_2.util.AppConstants.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Accept-Version: v1", "Authorization: CLIENT-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashDataModel
}
