package com.example.thenews.news.newsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thenews.databinding.ItemNewBinding
import com.example.thenews.model.presentation.New
import com.example.thenews.news.newsList.NewsListAdapter.NewsVH

class NewsListAdapter() : ListAdapter<New, NewsVH>(Differ) {

    class NewsVH(private val binding: ItemNewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(new: New) {
            binding.titleNew.text = new.title
            Glide.with(binding.imageNew).load(new.image).into(binding.imageNew)
            binding.favouriteButton.isVisible = false
            binding.notFavouriteButton.setOnClickListener {
                binding.notFavouriteButton.isVisible = false
                binding.favouriteButton.isVisible = true
            }
            binding.favouriteButton.setOnClickListener {
                binding.notFavouriteButton.isVisible = true
                binding.favouriteButton.isVisible = false
            }
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
