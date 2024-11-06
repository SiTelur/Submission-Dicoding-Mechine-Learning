package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.source.CancerRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: CancerRepository): ViewModel() {
    fun getHistory() = repository.getAnalize()
}