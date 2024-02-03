package com.example.thenews.news.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.news.newsList.NewsListAction.InitScreen
import com.example.thenews.news.newsList.NewsListAction.SearchNews
import com.example.thenews.news.newsList.NewsListState.Loading
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
        }
    }

    private fun fetchNewsList(query: String) {
        viewModelScope.launch {
            try {
                val news = repository.getNews(query)
                state.value = NewsListState.Success(news)
            } catch (e: Exception) {
                state.value = NewsListState.Error("Введите запрос")
            }
        }
    }
}
