package com.example.viewvault.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.viewvault.model.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}