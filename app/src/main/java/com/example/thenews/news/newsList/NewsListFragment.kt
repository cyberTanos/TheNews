package com.example.thenews.news.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thenews.R
import com.example.thenews.databinding.FragmentNewsListBinding
import com.example.thenews.news.content.ContentFragment.Companion.NEW_KEY
import com.example.thenews.news.newsList.NewListEffect.ToNavigateContentScreen
import com.example.thenews.news.newsList.NewsListAction.AddToFavourite
import com.example.thenews.news.newsList.NewsListAction.DeleteFromFavourite
import com.example.thenews.news.newsList.NewsListAction.InitScreen
import com.example.thenews.news.newsList.NewsListAction.OnClickNew
import com.example.thenews.news.newsList.NewsListAction.SearchNews
import com.example.thenews.news.newsList.NewsListState.Error
import com.example.thenews.news.newsList.NewsListState.Loading
import com.example.thenews.news.newsList.NewsListState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private lateinit var binding: FragmentNewsListBinding
    private val vm: NewsListVM by viewModels()
    private val adapter = NewsListAdapter(
        onClickNew = { new ->
            vm.doAction(OnClickNew(new))
        },
        addToFavourite = { new ->
            vm.doAction(AddToFavourite(new, binding.searchNews.text.toString()))
        },
        deleteFromFavourite = { new ->
            vm.doAction(DeleteFromFavourite(new, binding.searchNews.text.toString()))
        }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)

        bindUI()
        observeState()
        observeEffect()
        vm.doAction(InitScreen)

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
                        adapter.submitList(emptyList())
                    }
                }
            }
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.effect.collect { effect ->
                    when (effect) {
                        is ToNavigateContentScreen -> findNavController().navigate(
                            R.id.to_contentFragment,
                            bundleOf(NEW_KEY to effect.new)
                        )
                    }
                }
            }
        }
    }
}
