package com.example.thenews.news.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.news.favourite.FavouriteAction.InitScreen
import com.example.thenews.news.favourite.FavouriteState.Loading
import com.example.thenews.news.favourite.FavouriteState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteVM @Inject constructor(
    private val repository: NewsListRepository
) : ViewModel() {

    val state = MutableStateFlow<FavouriteState>(Loading)

    fun doAction(action: FavouriteAction) {
        when (action) {
            is InitScreen -> fetchFavouriteNews()
        }
    }

    private fun fetchFavouriteNews() {
        viewModelScope.launch {
            val newsFavourite = repository.getFavouritesNews()
            state.value = Success(newsFavourite)
        }
    }
}
