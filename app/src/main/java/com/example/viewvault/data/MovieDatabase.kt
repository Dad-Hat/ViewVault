package com.example.viewvault.data

import androidx.room.Database
import com.example.viewvault.model.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class MovieDatabase {
    abstract fun movieDao(): MovieDao
}