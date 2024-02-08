package com.example.thenews.news.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.model.presentation.New
import com.example.thenews.news.favourite.FavouriteAction.InitScreen
import com.example.thenews.news.favourite.FavouriteAction.OnClickDeleteFavouriteNew
import com.example.thenews.news.favourite.FavouriteAction.OnClickNew
import com.example.thenews.news.favourite.FavouriteEffect.ToNavigateContentScreen
import com.example.thenews.news.favourite.FavouriteState.Loading
import com.example.thenews.news.favourite.FavouriteState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavouriteVM @Inject constructor(
    private val repository: NewsListRepository
) : ViewModel() {

    val state = MutableStateFlow<FavouriteState>(Loading)
    private val _effect = Channel<FavouriteEffect>()
    val effect = _effect.receiveAsFlow()

    fun doAction(action: FavouriteAction) {
        when (action) {
            is InitScreen -> fetchFavouriteNews()
            is OnClickNew -> toNavigateContentScreen(action.new)
            is OnClickDeleteFavouriteNew -> deleteFN(action.favNew)
        }
    }

    private fun fetchFavouriteNews() {
        viewModelScope.launch {
            val newsFavourite = repository.getFavouritesNews()
            state.value = Success(newsFavourite)
        }
    }

    private fun toNavigateContentScreen(new: New) {
        _effect.trySend(ToNavigateContentScreen(new))
    }

    private fun deleteFN(favNew: New) {
        viewModelScope.launch {
            repository.deleteFavNew(favNew)
            fetchFavouriteNews()
        }
    }
}
