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
        val newsEntities = Mapper.map(newsResponse)
        dao.insertAll(*newsEntities.toTypedArray())
        val newsEntity = dao.getAll()
        return Mapper.map(newsEntity)
    }

    suspend fun addToFavourite(title: String): List<New> {
        val newEntity = dao.findByTitle(title)
        val new = newEntity.copy(isFavourite = !newEntity.isFavourite)
        dao.insertOne(new)
        return Mapper.map(dao.getAll())
    }

    suspend fun getFavouritesNews(): List<New> {
        val favouriteEntities = dao.getFavourites()
        return Mapper.map(favouriteEntities)
    }
}
