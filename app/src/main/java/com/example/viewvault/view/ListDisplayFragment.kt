package com.example.viewvault.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewvault.R
import com.example.viewvault.data.MoviesAdapter
import com.example.viewvault.databinding.ListDisplayFragmentBinding
import com.example.viewvault.listener.MovieListener
import com.example.viewvault.model.MovieModel
import com.example.viewvault.viewmodel.MovieViewModel

class ListDisplayFragment : Fragment(), MovieListener {
    private lateinit var binding : ListDisplayFragmentBinding
    private lateinit var navController: NavController
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movies: List<MovieModel>
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movies = emptyList()
        adapter = MoviesAdapter(movies, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDisplayFragmentBinding.inflate(layoutInflater)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        navController = findNavController()

        binding.apply {
            addMovieButton.setOnClickListener{
                navController.navigate(R.id.addMovieFragment)
            }

            searchBarEditText.addTextChangedListener {
                input ->
                    adapter.filter.filter(input)
            }
        }
        movieViewModel.fetchMovieList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieListObserver()
        setDelMovieObserver()

        val lManager = LinearLayoutManager(context)
        val theRecycler = binding.movieRecycler
        theRecycler.adapter = adapter
        theRecycler.layoutManager = lManager
    }

    private fun setMovieListObserver(){
        movieViewModel.movieList.observe(viewLifecycleOwner){
            binding.progressIndicator.visibility = View.VISIBLE
            binding.movieRecycler.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                adapter.updateList(it)
                binding.progressIndicator.visibility = View.GONE
                binding.movieRecycler.visibility = View.VISIBLE
            }, 1000)
        }
    }

    override fun onMovieItemClick(movieModel: MovieModel){
        movieViewModel.delMovie(movieModel)
    }

    override fun editMovieDetails(movieModel: MovieModel) {
        MovieDetailsDialog(movieModel){
            refreshCurrentFragment()
        }.show(requireActivity().supportFragmentManager, "dialog")
    }

    private fun setDelMovieObserver(){
        movieViewModel.delMovie.observe(viewLifecycleOwner){
            if(it != null){
                when(it){
                    "SUCCESS" -> {
                        movieViewModel.fetchMovieList()
                    }
                    "FAILURE" ->{

                    }
                }
            }
        }
    }

    private fun refreshCurrentFragment(){
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!, true)
        navController.navigate(id)
    }
}