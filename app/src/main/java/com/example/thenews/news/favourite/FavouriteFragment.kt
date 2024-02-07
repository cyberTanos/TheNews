package com.example.thenews.news.favourite

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
import com.example.thenews.databinding.FragmentFavouriteBinding
import com.example.thenews.news.favourite.FavouriteAction.InitScreen
import com.example.thenews.news.favourite.FavouriteAction.OnClickDeleteFavouriteNew
import com.example.thenews.news.favourite.FavouriteState.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
    private lateinit var binding: FragmentFavouriteBinding
    private val vm: FavouriteVM by viewModels()
    private val adapter = FavouriteAdapter { new ->
        vm.doAction(OnClickDeleteFavouriteNew(new))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        bindUI()
        observeState()
        vm.doAction(InitScreen)

        return binding.root
    }

    private fun bindUI() {
        binding.recyclerFavouriteNews.adapter = adapter
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    binding.isLoadingView.isVisible = state is FavouriteState.Loading
                    if (state is Success) {
                        adapter.submitList(state.newsList)
                    }
                }
            }
        }
    }
}
