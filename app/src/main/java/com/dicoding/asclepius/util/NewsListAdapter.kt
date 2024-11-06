package com.dicoding.asclepius.util

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory
import com.dicoding.asclepius.data.source.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ItemNewsItemBinding

class NewsListAdapter: ListAdapter<ArticlesItem, NewsListAdapter.NewsViewHolder>(DIFF_CALLBACK) {
    class NewsViewHolder(val binding: ItemNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val binding = ItemNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
       with(holder.binding){
            Glide.with(root.context).load(item.urlToImage).into(imgPoster)
           tvTitle.text = item.title
       }
    }
}