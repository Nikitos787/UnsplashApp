package com.example.composeunsplashapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeunsplashapp.data.local.dao.UnsplashImageDao
import com.example.composeunsplashapp.data.local.dao.UnsplashRemoteKeysDao
import com.example.composeunsplashapp.model.UnsplashImage
import com.example.composeunsplashapp.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1, exportSchema = false
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}
