package com.example.thenews.news.favourite

import com.example.thenews.model.presentation.New

interface FavouriteAction {
    object InitScreen : FavouriteAction
}

interface FavouriteState {
    object Loading : FavouriteState
    data class Success(
        val newsList: List<New>
    ) : FavouriteState
}
