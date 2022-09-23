package com.example.masteringpagingthree_2.data.model // ktlint-disable package-name

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "remoteKey")
data class UnsplashPhotoRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prev: Int?,
    val next: Int?
) : Parcelable
