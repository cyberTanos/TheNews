package com.example.thenews.news.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thenews.databinding.ItemFavouriteNewBinding
import com.example.thenews.model.presentation.New
import com.example.thenews.news.favourite.FavouriteAdapter.NewsVH

class FavouriteAdapter(private val onClickFavNew: (New) -> Unit, private val onDelete: (New) -> Unit) : ListAdapter<New, NewsVH>(Differ) {

    inner class NewsVH(private val binding: ItemFavouriteNewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(new: New) {
            binding.titleNew.text = new.title
            Glide.with(binding.imageNew).load(new.image).into(binding.imageNew)
            binding.root.setOnClickListener {
                onClickFavNew.invoke(new)
            }
            binding.deleteButton.setOnClickListener {
                onDelete.invoke(new)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemFavouriteNewBinding.inflate(inflate, parent, false)
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
