package com.example.viewvault.data

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.viewvault.databinding.MovieListItemBinding
import com.example.viewvault.listener.MovieListener
import com.example.viewvault.model.MovieModel

class MoviesAdapter(
    private var moviesList: List<MovieModel>,
    private var movieListener: MovieListener
): ListAdapter<MovieModel, MoviesAdapter.ViewHolder>(diffUtil), Filterable {

    private val searchFilter : Filter = object : Filter(){
        override fun performFiltering(input: CharSequence): FilterResults {
            val filteredList = if(input.isEmpty()) {
                moviesList
            }else{
                moviesList.filter { it.title.contains(input, ignoreCase = true) }
            }
            return FilterResults().apply { values = filteredList }
        }
        override fun publishResults(input: CharSequence, results: FilterResults) {
            val moviesList = results.values as List<MovieModel>
            submitList(moviesList.toMutableList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(from, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(currentList[position])
    }

    fun updateList(newList: List<MovieModel>){
        this.moviesList = newList
        submitList(newList.toList())
    }

    inner class ViewHolder(
        private val binding: MovieListItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bindItem(movieModel: MovieModel){
            binding.deleteButton.setOnClickListener {
                movieListener.onMovieItemClick(movieModel)
            }
            binding.textViewMovieName.text = movieModel.title
            binding.textViewSeenDate.text = "Seen: " + movieModel.seenDate
            binding.textViewReleaseDate.text = "Released: " + movieModel.releaseDate
            binding.textViewRating.text = "Rating: " + movieModel.rating.toString() + "/30"

            binding.movieDetailCard.setOnClickListener{
                movieListener.editMovieDetails(movieModel)
            }
        }
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<MovieModel>(){
            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel) =
                oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel) =
                oldItem.id == newItem.id
        }
    }
}