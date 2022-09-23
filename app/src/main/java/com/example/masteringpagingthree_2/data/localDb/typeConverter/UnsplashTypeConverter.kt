package com.example.masteringpagingthree_2.data.localDb.typeConverter // ktlint-disable package-name

import androidx.room.TypeConverter
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.google.gson.Gson

class UnsplashTypeConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromUnsplashPhotoUrls(unsplashPhotoUrls: UnsplashModel.UnsplashPhotoUrls): String =
            Gson().toJson(unsplashPhotoUrls)

        @JvmStatic
        @TypeConverter
        fun toUnsplashPhotoUrls(unsplashPhotoUrlsStringFormat: String): UnsplashModel.UnsplashPhotoUrls =
            Gson().fromJson(
                unsplashPhotoUrlsStringFormat,
                UnsplashModel.UnsplashPhotoUrls::class.java
            )

        @JvmStatic
        @TypeConverter
        fun fromUnsplashUser(unsplashUser: UnsplashModel.UnsplashUser): String =
            Gson().toJson(unsplashUser)

        @JvmStatic
        @TypeConverter
        fun toUnsplashUser(unsplashUserStringFormat: String): UnsplashModel.UnsplashUser =
            Gson().fromJson(
                unsplashUserStringFormat,
                UnsplashModel.UnsplashUser::class.java
            )
    }
}
