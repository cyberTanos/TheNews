package com.example.thenews.news.newsList

import com.example.thenews.model.presentation.New

interface NewsListAction {
    object InitScreen : NewsListAction
    data class SearchNews(
        val searchQuery: String
    ) : NewsListAction

    data class OnClickNew(
        val new: New
    ) : NewsListAction

    data class AddToFavourite(
        val favouriteNew: New,
        val searchQuery: String
    ) : NewsListAction

    data class DeleteFromFavourite(
        val favouriteNew: New,
        val searchQuery: String
    ) : NewsListAction
}

interface NewsListState {
    object Loading : NewsListState
    data class Success(
        val newsList: List<New>
    ) : NewsListState

    data class Error(
        val errorMessage: String
    ) : NewsListState
}

interface NewListEffect {
    data class ToNavigateContentScreen(
        val new: New
    ) : NewListEffect
}
