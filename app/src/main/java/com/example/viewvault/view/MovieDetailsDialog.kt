package com.example.viewvault.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewvault.R
import com.example.viewvault.databinding.EditMovieDialogBinding
import com.example.viewvault.model.MovieModel
import com.example.viewvault.utils.Util
import com.example.viewvault.viewmodel.MovieViewModel

class MovieDetailsDialog(
    private val movieModel: MovieModel,
    private val onDismiss: () -> Unit
): DialogFragment() {
    private lateinit var binding: EditMovieDialogBinding
    private lateinit var movie: MovieModel
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Popup_Dialog)
        movie = movieModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditMovieDialogBinding.inflate(layoutInflater)

        binding.ratingPicker.minValue = 1
        binding.ratingPicker.maxValue = 30
        binding.ratingPicker.textColor = resources.getColor(R.color.white)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        binding.movieTitleEditText.setText(movie.title)

        if(movie.summary.isNotEmpty()){
            binding.movieSummaryEditText.setText(movie.summary)
        }

        binding.ratingPicker.value = movie.rating
        binding.seenDateTextView.setText(movie.seenDate)
        binding.releaseDateTextView.setText(movie.releaseDate)

        binding.editReleaseButton.setOnClickListener {
            Util.displayCalendar(requireContext(), binding.releaseDateTextView)
        }

        binding.editSeenButton.setOnClickListener {
            Util.displayCalendar(requireContext(), binding.seenDateTextView)
        }

        binding.updateDetailsButton.setOnClickListener {
            checkDetails()
            onDismiss.invoke()
            dismiss()
        }
    }

    private fun checkDetails(){
        if(Util.hasText(binding.movieTitleEditText)){
            val updatedTitle = binding.movieTitleEditText.text.toString()
            val updatedSum = binding.movieSummaryEditText.text.toString()
            val updatedRating = binding.ratingPicker.value
            val updatedRelease = binding.releaseDateTextView.text.toString()
            val updatedSeen = binding.seenDateTextView.text.toString()

            val newMovie = MovieModel(updatedTitle, updatedSum, updatedRating, updatedSeen, updatedRelease, movie.id)
            movieViewModel.updateMovieDetails(newMovie)
        }
    }
}