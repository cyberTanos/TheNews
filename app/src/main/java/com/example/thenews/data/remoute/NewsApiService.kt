package com.example.thenews.data.remoute

import com.example.thenews.model.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = "06f848bf54224ebea2f86ea84c7c6324"
    ): NewsResponse
}
