package com.example.thenews.newsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenews.domain.NewsListRepository
import com.example.thenews.newsList.NewsListAction.InitScreen
import com.example.thenews.newsList.NewsListState.Loading
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
            is InitScreen -> fetchNewsList()
        }
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            val news = repository.getNews()
            state.value = NewsListState.Success(news)
        }
    }
}
