package com.example.viewvault

import android.app.Application
import androidx.room.Room
import com.example.viewvault.data.MovieDatabase

class AppModule: Application() {

    companion object {
        lateinit var database: MovieDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }
}