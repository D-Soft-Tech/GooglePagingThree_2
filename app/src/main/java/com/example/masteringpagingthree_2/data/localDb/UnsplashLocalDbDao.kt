package com.example.masteringpagingthree_2.data.localDb // ktlint-disable package-name

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.masteringpagingthree_2.data.model.UnsplashModel
import com.example.masteringpagingthree_2.data.model.UnsplashPhotoRemoteKey

@Dao
interface UnsplashLocalDbDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotos(photo: UnsplashModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListOfPhotos(photos: UnsplashModel)

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): PagingSource<Int, UnsplashModel>

    @Query("DELETE FROM photo")
    suspend fun deleteAllPhotos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(listOfRemoteKeys: UnsplashPhotoRemoteKey)

    @Query("SELECT * FROM remoteKey WHERE id = :id")
    suspend fun getAllRemoteKeys(id: String): UnsplashPhotoRemoteKey?

    @Query("DELETE FROM remoteKey")
    suspend fun deleteAllRemoteKeys()
}
