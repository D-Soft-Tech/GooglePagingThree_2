package com.example.masteringpagingthree_2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentUserCollection(
    val cover_photo: CoverPhoto,
    val id: Int,
    val last_collected_at: String,
    val published_at: String,
    val title: String,
    val updated_at: String,
    val user: User
) : Parcelable