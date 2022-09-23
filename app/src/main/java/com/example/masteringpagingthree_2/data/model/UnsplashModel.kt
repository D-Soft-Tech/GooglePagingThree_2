package com.example.masteringpagingthree_2.data.model // ktlint-disable package-name

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "photo")
data class UnsplashModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
) : Parcelable {
    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl
            get() =
                "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}
