package com.example.thenews.domain

import com.example.thenews.model.presentation.New
import com.example.thenews.model.response.NewsResponse

object Mapper {

    fun map(response: NewsResponse): List<New> {
        return response.articles.map {
            New(
                title = it.title,
                image = it.urlToImage.toString()
            )
        }
    }
}
