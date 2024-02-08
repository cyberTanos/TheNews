package com.example.thenews.domain

import com.example.thenews.model.entity.NewEntity
import com.example.thenews.model.presentation.New
import com.example.thenews.model.response.NewsResponse

object Mapper {

    fun mapToNews(response: NewsResponse, entities: List<NewEntity>): List<New> {
        return response.articles.map { resp ->
            val isFavourite = entities.find { it.title == resp.title } != null
            New(
                title = resp.title,
                image = resp.urlToImage.toString(),
                description = resp.description,
                url = resp.url,
                isFavourite = isFavourite
            )
        }
    }

    fun map(entities: List<NewEntity>): List<New> {
        return entities.map {
            New(
                title = it.title,
                image = it.image,
                description = it.description,
                url = it.url,
                isFavourite = it.isFavourite
            )
        }
    }

    fun map(new: New): NewEntity {
        return NewEntity(
            title = new.title,
            image = new.image,
            description = new.description,
            url = new.url,
            isFavourite = new.isFavourite
        )
    }
}
