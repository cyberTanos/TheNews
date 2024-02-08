package com.example.thenews.news.content

import androidx.lifecycle.ViewModel
import com.example.thenews.news.content.ContentAction.InitScreen
import com.example.thenews.news.content.ContentAction.OnClickBack
import com.example.thenews.news.content.ContentEffect.ReturnToPreviousBack
import com.example.thenews.news.content.ContentState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class ContentVM @Inject constructor(

) : ViewModel() {

    private val state = MutableStateFlow<ContentState>(Loading)
    private val _effect = Channel<ContentEffect>()
    val effect = _effect.receiveAsFlow()

    fun doAction(action: ContentAction) {
        when (action) {
            is InitScreen -> fetchNew()
            is OnClickBack -> toNavigate()
        }
    }

    private fun fetchNew() {

    }

    private fun toNavigate() {
        _effect.trySend(ReturnToPreviousBack)
    }
}
