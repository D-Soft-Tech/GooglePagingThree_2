package com.example.masteringpagingthree_2.data.remote.apiService // ktlint-disable package-name

import com.example.masteringpagingthree_2.data.model.UnsplashPhotoResponse
import com.example.masteringpagingthree_2.util.AppConstants.CLIENT_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<UnsplashPhotoResponse>
}
