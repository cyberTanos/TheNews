package com.example.thenews.domain

import com.example.thenews.model.entity.NewEntity
import com.example.thenews.model.presentation.New
import com.example.thenews.model.response.NewsResponse

object Mapper {

    fun map(response: NewsResponse): List<NewEntity> {
        return response.articles.map {
            NewEntity(
                title = it.title,
                image = it.urlToImage.toString()
            )
        }
    }

    fun map(entities: List<NewEntity>): List<New> {
        return entities.map {
            New(
                title = it.title,
                image = it.image,
                isFavourite = it.isFavourite
            )
        }
    }
}
