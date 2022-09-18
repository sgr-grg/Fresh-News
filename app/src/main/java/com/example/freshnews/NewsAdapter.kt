package com.example.freshnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freshnews.databinding.NewsItemBinding

class NewsAdapter(private val onItemClicked: (item: News) -> Unit) : ListAdapter<News, NewsAdapter.NewsViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindTo(item)
        }
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(news: News) {
            binding.title.text = news.title
            binding.description.text = news.description
            binding.subTitle.text = news.author
            binding.time.text = news.publishedAt
            Glide.with(itemView.context).load(news.urlToImage).into(binding.imageView)
            itemView.setOnClickListener {
                onItemClicked.invoke(news)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
        }
    }
}