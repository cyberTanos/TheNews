package com.example.thenews.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thenews.databinding.ItemNewBinding
import com.example.thenews.model.presentation.New
import com.example.thenews.newsList.NewsListAdapter.NewsVH

class NewsListAdapter() : ListAdapter<New, NewsVH>(Differ) {

    class NewsVH(private val binding: ItemNewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(new: New) {
            binding.titleNew.text = new.title
            Glide.with(binding.imageNew).load(new.image).into(binding.imageNew)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemNewBinding.inflate(inflate, parent, false)
        return NewsVH(binding)
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val itemNew = getItem(position)
        holder.bind(itemNew)
    }

    object Differ : DiffUtil.ItemCallback<New>(

    ) {
        override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
            return oldItem == newItem
        }
    }
}
