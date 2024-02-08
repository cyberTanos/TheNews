package com.example.thenews.domain

import com.example.thenews.data.local.NewDao
import com.example.thenews.data.remoute.NewsApiService
import com.example.thenews.model.presentation.New
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val api: NewsApiService,
    private val dao: NewDao
) {

    suspend fun getNews(query: String): List<New> {
        val newsResponse = api.getNews(q = query)
        val newsEntity = dao.getAll()
        return Mapper.mapToNews(newsResponse, newsEntity)
    }

    suspend fun addToFavourite(new: New) {
        dao.insertOne(Mapper.map(new))
    }

    suspend fun getFavouritesNews(): List<New> {
        val favouriteEntities = dao.getAll()
        return Mapper.map(favouriteEntities)
    }

    suspend fun deleteFavNew(favNew: New) {
        val newEntity = Mapper.map(favNew)
        dao.deleteFavNew(newEntity)
    }
}
