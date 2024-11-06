package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.source.Result
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.util.HistoryListAdapter
import com.dicoding.asclepius.util.ViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyListAdapter: HistoryListAdapter

    private val historyActivityViewwModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            historyListAdapter = HistoryListAdapter()
            rvHistory.adapter = historyListAdapter
        }

        historyActivityViewwModel.getHistory().observe(this){ list->
            historyListAdapter.submitList(list)
        }


    }
}