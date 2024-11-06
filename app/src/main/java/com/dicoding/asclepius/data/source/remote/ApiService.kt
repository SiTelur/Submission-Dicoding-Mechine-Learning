package com.dicoding.asclepius.data.source.remote



import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.source.remote.response.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("top-headlines?q=cancer&category=health&language=en&apiKey=${BuildConfig.API_KEY}")
    suspend fun getNews() : NewsResponse
}