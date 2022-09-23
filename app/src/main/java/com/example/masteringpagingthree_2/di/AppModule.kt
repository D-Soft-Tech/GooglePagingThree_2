package com.example.masteringpagingthree_2.di // ktlint-disable package-name

import android.content.Context
import androidx.room.Room
import com.example.masteringpagingthree_2.BuildConfig
import com.example.masteringpagingthree_2.data.localDb.UnsplashLocalDbDao
import com.example.masteringpagingthree_2.data.localDb.UnsplashRoomDb
import com.example.masteringpagingthree_2.data.remote.apiService.ApiService
import com.example.masteringpagingthree_2.util.AppConstants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesOKHTTPClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val mCache = Cache(context.cacheDir, cacheSize)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder()
                .cache(mCache)
                .retryOnConnectionFailure(true)
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()
        }
    }

    @Singleton
    @Provides
    fun providesUnsplashPhotoDb(
        @ApplicationContext appContext: Context
    ): UnsplashRoomDb =
        Room.databaseBuilder(
            appContext,
            UnsplashRoomDb::class.java,
            "unsplashPhotos-db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providesLocalDao(
        unsplashDb: UnsplashRoomDb
    ): UnsplashLocalDbDao = unsplashDb.getUnsplashLocalDbDao()
}
