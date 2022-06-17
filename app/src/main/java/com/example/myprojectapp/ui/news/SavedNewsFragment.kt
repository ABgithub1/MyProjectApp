package com.example.myprojectapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myprojectapp.R
import com.example.myprojectapp.adapter.ArticlesListAdapter
import com.example.myprojectapp.databinding.FragmentSavedNewsBinding
import com.example.myprojectapp.extentions.SwipeToDeleteCallback
import com.example.myprojectapp.extentions.addSpaceDecoration
import com.example.myprojectapp.model.LceState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<SavedNewsViewModel>()

    private val adapter by lazy {
        ArticlesListAdapter(
            itemClick = {

            },
            longItemClick = {

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSavedNewsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addSpaceDecoration(8)
            recyclerView.adapter = adapter



            viewModel.lceDatabaseFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { lce ->
                    binding.progressCircular.isVisible = lce == LceState.Loading
                    when (lce) {
                        is LceState.Content -> {
                            lce.articles.collect {
                                adapter.submitList(it)
                            }
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                view,
                                lce.throwable.message ?: "State_Error",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                        LceState.Loading -> {
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val articleToDel = adapter.currentList[viewHolder.adapterPosition]
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.onArticleSwiped(articleToDel)
                }
            }
        }
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.recyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}