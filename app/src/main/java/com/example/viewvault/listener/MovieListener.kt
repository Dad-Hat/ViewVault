package com.example.viewvault.listener

import com.example.viewvault.model.MovieModel

interface MovieListener {
    fun onMovieItemClick(movieModel: MovieModel)
    fun editMovieDetails(movieModel: MovieModel)
}