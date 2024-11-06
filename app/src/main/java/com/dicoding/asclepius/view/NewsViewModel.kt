package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.source.CancerRepository

class NewsViewModel(private val repository: CancerRepository) : ViewModel() {
     fun getNews() = repository.getNews()
}