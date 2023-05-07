package com.example.genesis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.genesis.databinding.BookItemBinding
import com.example.genesis.remote.BookDataModel

class BookAdapter(
    var languageList: List<BookDataModel>,
    var loadNextData: (genre : String,position : Int) -> Unit
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                binding.tvChapterName.text = this.name
                Glide.with(itemView.context)
                    .load(this.coverUrl)
                    .into(binding.ivChapter)
                binding.container.setOnClickListener {
                    loadNextData(this.genre,position)
                }
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return languageList.size
    }
}