package com.example.thenews.domain

import com.example.thenews.data.removed.NewsApiService
import com.example.thenews.model.presentation.New
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val api: NewsApiService
) {

    suspend fun getNews(query: String): List<New> {
        val new = api.getNews(q = query)
        return Mapper.map(new)
    }
}
