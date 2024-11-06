package com.dicoding.asclepius.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory
import com.dicoding.asclepius.data.source.local.room.AnalyzeDao
import com.dicoding.asclepius.data.source.remote.ApiService
import com.dicoding.asclepius.data.source.remote.response.ArticlesItem

class CancerRepository(private val apiService: ApiService,private val analyzeDao: AnalyzeDao) {

    companion object {
        private const val ALL = -1


        @Volatile
        private var instance: CancerRepository? = null
        fun getInstance(
            apiService: ApiService,
            analyzeDao: AnalyzeDao,

        ): CancerRepository = instance ?: synchronized(this) {
            instance ?: CancerRepository(apiService, analyzeDao)
        }.also { instance = it }
    }

    fun getNews() : LiveData<Result<List<ArticlesItem?>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews()
            val articles = response.articles
            emit(Result.Success(articles!!))
        }catch (e:Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveAnalyze(analyzeHistory: AnalyzeHistory) {
        analyzeDao.insert(analyzeHistory)
    }

    fun  getAnalize() : LiveData<List<AnalyzeHistory>> {
        return analyzeDao.getHistory()
    }
}