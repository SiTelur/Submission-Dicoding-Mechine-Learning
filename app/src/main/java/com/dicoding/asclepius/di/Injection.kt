package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.source.CancerRepository
import com.dicoding.asclepius.data.source.local.room.AnalyzeRoomDatabase
import com.dicoding.asclepius.data.source.remote.ApiConfig

object Injection {
    fun provideAnalyzeRepository(context: Context) : CancerRepository {
        val apiService = ApiConfig.getApiService()
        val database = AnalyzeRoomDatabase.getDatabase(context)
        val eventDao = database.analyzeDao()
        return CancerRepository.getInstance(apiService,eventDao)
    }
}