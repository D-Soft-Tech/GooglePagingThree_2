package com.example.masteringpagingthree_2.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashDataModel(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
) : Parcelable