package com.example.thenews.news.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.thenews.R
import com.example.thenews.databinding.FragmentContentBinding
import com.example.thenews.model.presentation.New
import com.example.thenews.news.content.ContentAction.InitScreen
import com.example.thenews.news.content.ContentAction.OnClickBack
import com.example.thenews.news.content.ContentEffect.ReturnToPreviousBack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : Fragment(R.layout.fragment_content) {

    private lateinit var binding: FragmentContentBinding
    private val vm: ContentVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContentBinding.inflate(inflater, container, false)

        bindUI()
        observeEffect()
        vm.doAction(InitScreen)
        val args = arguments?.getParcelable<New>(NEW_KEY)!!
        binding.titleNew.text = args.title
        Glide.with(binding.imageNew).load(args.image).into(binding.imageNew)
        binding.descriptionNew.text = "    " + args.description
        binding.urlNew.text = args.url

        return binding.root
    }

    private fun bindUI() {
        binding.backButton.setOnClickListener {
            vm.doAction(OnClickBack)
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.effect.collect { effect ->
                    when (effect) {
                        is ReturnToPreviousBack -> findNavController().popBackStack()
                    }
                }
            }
        }
    }

    companion object {
        const val NEW_KEY = "newKey"
    }
}
