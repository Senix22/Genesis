package com.example.genesis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.genesis.databinding.AlsoLikeItemBinding
import com.example.genesis.databinding.BookItemBinding
import com.example.genesis.remote.BookDataModel

class YouAlsoLikeAdapter(var itemList :List<BookDataModel>) : RecyclerView.Adapter<YouAlsoLikeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AlsoLikeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AlsoLikeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(itemList[position]){
                binding.tvChapterName.text = this.name
                Glide.with(itemView.context)
                    .load(this.coverUrl)
                    .into(binding.ivChapter)
                binding.tvChapterName.setOnClickListener {
//                    loadNextData()
                }
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return itemList.size
    }
}