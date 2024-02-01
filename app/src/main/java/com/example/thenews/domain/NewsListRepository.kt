package com.example.thenews.domain

import com.example.thenews.model.presentation.New
import javax.inject.Inject
import kotlinx.coroutines.delay

class NewsListRepository @Inject constructor(

) {

    suspend fun getNews(): List<New> {
        delay(2000)
        return listOf(
            New(title = "News Lol"),
            New(title = "Kek pop"),
            New(title = "Pop pep you")
        )
    }
}
