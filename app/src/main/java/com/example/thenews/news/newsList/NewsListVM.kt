package com.example.thenews.news.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.model.presentation.New
import com.example.thenews.news.newsList.NewsListAction.InitScreen
import com.example.thenews.news.newsList.NewsListAction.OnClickFavourite
import com.example.thenews.news.newsList.NewsListAction.SearchNews
import com.example.thenews.news.newsList.NewsListState.Loading
import com.example.thenews.news.newsList.NewsListState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NewsListVM @Inject constructor(
    private val repository: NewsListRepository
) : ViewModel() {

    val state = MutableStateFlow<NewsListState>(Loading)

    fun doAction(action: NewsListAction) {
        when (action) {
            is InitScreen -> fetchNewsList(query = "Кино")
            is SearchNews -> fetchNewsList(query = action.searchQuery)
            is OnClickFavourite -> addToFavouriteNew(action.favouriteNew)
        }
    }

    private fun fetchNewsList(query: String) {
        viewModelScope.launch {
            try {
                val newsResponse = repository.getNews(query)
                state.value = Success(newsResponse)
            } catch (e: Exception) {
                state.value = NewsListState.Error(e.message.toString())
            }
        }
    }

    private fun addToFavouriteNew(newFavourite: New) {
        viewModelScope.launch {
            val news = repository.addToFavourite(title = newFavourite.title)
            state.value = Success(news)
        }
    }
}
