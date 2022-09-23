package com.example.masteringpagingthree_2.data.localDb // ktlint-disable package-name

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.masteringpagingthree_2.data.localDb.typeConverter.UnsplashTypeConverter
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.data.model.UnsplashPhotoRemoteKey

@Database(
    entities = [UnsplashModel::class, UnsplashPhotoRemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UnsplashTypeConverter::class)
abstract class UnsplashRoomDb : RoomDatabase() {
    abstract fun getUnsplashLocalDbDao(): UnsplashLocalDbDao
}
