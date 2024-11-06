package com.dicoding.asclepius.util

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory
import com.dicoding.asclepius.databinding.HistoryItemBinding
import com.dicoding.asclepius.view.HistoryActivity

class HistoryListAdapter() : ListAdapter<AnalyzeHistory,HistoryListAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    class HistoryViewHolder(val binding : HistoryItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnalyzeHistory>() {
            override fun areItemsTheSame(
                oldItem: AnalyzeHistory,
                newItem: AnalyzeHistory,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AnalyzeHistory,
                newItem: AnalyzeHistory,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history =getItem(position)
        holder.binding.imgResult.setImageURI(Uri.parse(history.imageUri))
        holder.binding.tvResult.text = history.resultRate
        holder.binding.tvDate.text = history.insertedDate
    }
}