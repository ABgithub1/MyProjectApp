package com.example.myprojectapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectapp.adapter.ArticlesListAdapter
import com.example.myprojectapp.databinding.FragmentTopNewsBinding
import com.example.myprojectapp.extentions.addPaginationScrollListener
import com.example.myprojectapp.extentions.addSpaceDecoration
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.ui.home.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TopNewsFragment : Fragment() {
    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<TopNewsViewModel>()

    private val adapter by lazy {
        ArticlesListAdapter(
            itemClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToWebViewFragment(
                        it.url.toString()
                    )
                )
            },
            longItemClick = {
                viewModel.saveArticle(article = it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTopNewsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
            recyclerView.addSpaceDecoration(8)

            recyclerView.addPaginationScrollListener(linearLayoutManager, ITEMS_TO_LOAD) {
                viewModel.onLoadMore()
                Toast.makeText(context, "new page", Toast.LENGTH_SHORT).show()
            }

            viewModel.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { lceState ->
                    binding.progressCircular.isVisible = lceState == LceState.Loading
                    when (lceState) {
                        is LceState.Content -> {
                            adapter.submitList(lceState.articles)
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                view,
                                lceState.throwable.message ?: "State_Error",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                        is LceState.Loading -> {}
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    companion object {
        const val ITEMS_TO_LOAD = 5
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}