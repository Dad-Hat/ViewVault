package com.example.viewvault.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.viewvault.model.MovieModel

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieModel>

    @Query("DELETE FROM movies")
    fun wipeTable()

    @Insert
    fun insertMovie(movieModel: MovieModel)
}