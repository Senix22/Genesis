package com.example.genesis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.genesis.databinding.ViewPagerBookItemBinding
import com.example.genesis.remote.BookDataModel

class ViewPagerAdapter(
    private val labelList: List<BookDataModel>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = ViewPagerBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val label = labelList[position]
        holder.bind(label)
    }

    override fun getItemCount(): Int {
        return labelList.size
    }

   inner class ViewPagerHolder(private var itemHolderBinding: ViewPagerBookItemBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {
        fun bind(label: BookDataModel) {
            itemHolderBinding.label.text = label.name
            itemHolderBinding.author.text = label.author
            Glide.with(itemView.context)
                .load(label.coverUrl)
                .into(itemHolderBinding.image)
        }
    }
}
