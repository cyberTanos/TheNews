package com.example.thenews.news.content

import com.example.thenews.model.presentation.New

interface ContentAction {
    object InitScreen : ContentAction
    object OnClickBack : ContentAction
}

interface ContentState {
    object Loading : ContentState
    data class Success(
        val new: New
    ) : ContentState
}

interface ContentEffect {
    object ReturnToPreviousBack : ContentEffect
}
