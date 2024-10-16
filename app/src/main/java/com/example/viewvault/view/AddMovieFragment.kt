package com.example.viewvault.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.viewvault.databinding.AddMovieFragmentBinding
import com.example.viewvault.model.MovieModel
import com.example.viewvault.viewmodel.MovieViewModel
import java.util.Calendar

class AddMovieFragment: Fragment() {

    private lateinit var binding: AddMovieFragmentBinding
    private lateinit var numPicker: NumberPicker
    private lateinit var navController: NavController
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddMovieFragmentBinding.inflate(layoutInflater)
        numPicker = binding.ratingPicker
        numPicker.maxValue = 30
        numPicker.minValue = 1

        navController = findNavController()
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        setListeners()
        return binding.root
    }



    private fun setListeners() {
        binding.releaseDateButton.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                {
                    view, year, monthOfYear, dayOfMonth ->
                    val dat = ((monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year)
                    binding.textViewReleaseDate.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.seenDateButton.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                {
                        view, year, monthOfYear, dayOfMonth ->
                    val dat = ((monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year)
                    binding.textViewSeenDate.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.submitButton.setOnClickListener {
            val movieTitle = binding.editTextMovieTitle.text.toString()
            val movieSum = binding.editTextMovieSummary.text.toString()
            val rating = binding.ratingPicker.value
            val seenDate = binding.textViewSeenDate.text.toString()
            val releaseDate = binding.textViewReleaseDate.text.toString()

            val newMovie = MovieModel(movieTitle, movieSum, rating, seenDate, releaseDate)
            movieViewModel.addMovie(newMovie)
            navController.popBackStack()
        }

        binding.closeButton.setOnClickListener{
            navController.popBackStack()
        }
    }

}