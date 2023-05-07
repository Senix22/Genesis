package com.example.genesis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genesis.databinding.GenreItemBinding
import com.example.genesis.remote.BookDataModel

class GenreAdapter(
    var genreList: Set<String>, var languageList: List<BookDataModel>, var loadNextData: (genre : String,position : Int) -> Unit
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.tvSubjectName.text = genreList.elementAt(position)
            with(languageList[position]) {
                binding.rvChapters.apply {
                    layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter =
                        BookAdapter(languageList.filter { it -> it.genre == binding.tvSubjectName.text }) { position, gener ->
                            loadNextData(position,gener)
                        }
                }
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return genreList.size
    }
}