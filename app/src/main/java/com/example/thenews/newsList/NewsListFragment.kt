package com.example.thenews.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thenews.R
import com.example.thenews.databinding.FragmentNewsListBinding
import com.example.thenews.newsList.NewsListAction.InitScreen
import com.example.thenews.newsList.NewsListAction.SearchNews
import com.example.thenews.newsList.NewsListState.Error
import com.example.thenews.newsList.NewsListState.Loading
import com.example.thenews.newsList.NewsListState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private lateinit var binding: FragmentNewsListBinding
    private val vm: NewsListVM by viewModels()
    private val adapter = NewsListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        bindUI()
        vm.doAction(InitScreen)
        observeState()

        return binding.root
    }

    private fun bindUI() {
        binding.recyclerNewsList.adapter = adapter
        binding.buttonSearchNews.setOnClickListener {
            vm.doAction(SearchNews(binding.searchNews.text.toString()))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    binding.isLoadingView.isVisible = state is Loading
                    binding.errorEmpty.isVisible = state is Error
                    if (state is Success) {
                        adapter.submitList(state.newsList)
                    }
                    if (state is Error) {
                        binding.errorEmpty.text = state.errorMessage
                    }
                }
            }
        }
    }
}
