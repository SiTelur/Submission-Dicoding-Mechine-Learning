package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.source.CancerRepository
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory
import kotlinx.coroutines.launch

class ResultViewModel(private val cancerRepository: CancerRepository) : ViewModel() {
    fun insertData(analyzeHistory: AnalyzeHistory) {
      viewModelScope.launch { cancerRepository.saveAnalyze(analyzeHistory) }
    }
}