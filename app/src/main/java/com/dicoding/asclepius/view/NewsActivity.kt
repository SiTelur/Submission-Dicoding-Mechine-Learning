package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.source.Result
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.util.HistoryListAdapter
import com.dicoding.asclepius.util.NewsListAdapter
import com.dicoding.asclepius.util.ViewModelFactory

class NewsActivity : AppCompatActivity() {
    lateinit var binding : ActivityNewsBinding
    lateinit var newsListAdapter: NewsListAdapter
    private val newsViewModel by viewModels<NewsViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            rvNews.layoutManager = LinearLayoutManager(this@NewsActivity)
            newsListAdapter = NewsListAdapter()
            rvNews.adapter = newsListAdapter
        }

        newsViewModel.getNews().observe(this){ news ->
           when(news){
               is Result.Error -> showToast("Gagal Mendapatkan Data")
               Result.Loading -> {binding.progressBar.visibility = View.VISIBLE}
               is Result.Success -> {
                   binding.progressBar.visibility = View.GONE
                   newsListAdapter.submitList(news.data)
               }
           }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}