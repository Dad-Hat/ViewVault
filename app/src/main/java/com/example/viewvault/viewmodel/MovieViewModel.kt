package com.example.viewvault.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewvault.AppModule
import com.example.viewvault.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private var _addMovie = MutableLiveData<String>()
    private var _delMovie = MutableLiveData<String>()

    val addMovie
        get() = _addMovie

    val delMovie
        get() = _delMovie

    private var _movieList = MutableLiveData<List<MovieModel>>()

    val movieList
        get() = _movieList

    fun fetchMovieList(){
        viewModelScope.launch (Dispatchers.IO){
            val movies = AppModule.database.movieDao().getAll()
            _movieList.postValue(movies)
        }
    }

    fun addMovie(movie: MovieModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                AppModule.database.movieDao().insertMovie(movie)
                _addMovie.postValue("success")
            } catch (ex: Exception){
                _addMovie.postValue("failure")
            }
        }
    }

    fun delMovie(movie: MovieModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                AppModule.database.movieDao().deleteMovie(movie)
                _delMovie.postValue("SUCCESS")
            } catch (ex: Exception){
                _delMovie.postValue("FAILURE")
            }
        }
    }

    fun updateMovieDetails(newMovie: MovieModel){
        viewModelScope.launch (Dispatchers.IO){
            try{
                AppModule.database.movieDao().updateMovie(newMovie)
                _addMovie.postValue("success")
            }catch (ex: Exception){
                _addMovie.postValue("failure")
            }
        }
    }
}