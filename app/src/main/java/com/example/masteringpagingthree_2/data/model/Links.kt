package com.example.masteringpagingthree_2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    val download: String,
    val html: String,
    val self: String
) : Parcelable