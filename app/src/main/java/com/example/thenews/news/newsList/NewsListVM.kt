package com.example.thenews.news.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.model.presentation.New
import com.example.thenews.news.newsList.NewListEffect.ToNavigateContentScreen
import com.example.thenews.news.newsList.NewsListAction.AddToFavourite
import com.example.thenews.news.newsList.NewsListAction.DeleteFromFavourite
import com.example.thenews.news.newsList.NewsListAction.InitScreen
import com.example.thenews.news.newsList.NewsListAction.OnClickNew
import com.example.thenews.news.newsList.NewsListAction.SearchNews
import com.example.thenews.news.newsList.NewsListState.Loading
import com.example.thenews.news.newsList.NewsListState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewsListVM @Inject constructor(
    private val repository: NewsListRepository
) : ViewModel() {

    val state = MutableStateFlow<NewsListState>(Loading)
    private val _effect = Channel<NewListEffect>()
    val effect = _effect.receiveAsFlow()
    private var search: String = "Кино"

    fun doAction(action: NewsListAction) {
        when (action) {
            is InitScreen -> fetchNewsList()
            is SearchNews -> {
                search = action.searchQuery
                fetchNewsList()
            }

            is OnClickNew -> toNavigate(action.new)
            is AddToFavourite -> {
                search = action.searchQuery
                addToFavouriteNew(action.favouriteNew)
            }

            is DeleteFromFavourite -> {
                search = action.searchQuery
                deleteFromFavourite(action.favouriteNew)
            }
        }
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            try {
                val checkedQuery = search.ifEmpty { "Кино" }
                val newsResponse = repository.getNews(checkedQuery)
                state.value = Success(newsResponse)
            } catch (e: Exception) {
                state.value = NewsListState.Error(e.message.toString())
            }
        }
    }

    private fun toNavigate(new: New) {
        _effect.trySend(ToNavigateContentScreen(new))
    }

    private fun addToFavouriteNew(newFavourite: New) {
        viewModelScope.launch {
            val checkedQuery = search.ifEmpty { "Кино" }
            repository.addToFavourite(newFavourite)
            val news = repository.getNews(checkedQuery)
            state.value = Success(news)
        }
    }

    private fun deleteFromFavourite(newFavourite: New) {
        viewModelScope.launch {
            val checkedQuery = search.ifEmpty { "Кино" }
            repository.deleteFavNew(newFavourite)
            val news = repository.getNews(checkedQuery)
            state.value = Success(news)
        }
    }
}
