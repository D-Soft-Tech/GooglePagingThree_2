package com.example.masteringpagingthree_2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Parcelable