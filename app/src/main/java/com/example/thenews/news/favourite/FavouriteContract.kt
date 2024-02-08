package com.example.thenews.news.favourite

import com.example.thenews.model.presentation.New

interface FavouriteAction {
    object InitScreen : FavouriteAction
    data class OnClickNew(
        val new: New
    ) : FavouriteAction

    data class OnClickDeleteFavouriteNew(
        val favNew: New
    ) : FavouriteAction
}

interface FavouriteState {
    object Loading : FavouriteState
    data class Success(
        val newsList: List<New>
    ) : FavouriteState
}

interface FavouriteEffect {
    data class ToNavigateContentScreen(
        val new: New
    ) : FavouriteEffect
}
