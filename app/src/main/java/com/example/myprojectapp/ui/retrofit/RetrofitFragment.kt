package com.example.myprojectapp.ui.retrofit

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectapp.adapter.ArticlesListAdapter
import com.example.myprojectapp.databinding.FragmentRetrofitBinding
import com.example.myprojectapp.extentions.addPaginationScrollListener
import com.example.myprojectapp.model.LceState
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class RetrofitFragment : Fragment() {
    private var _binding: FragmentRetrofitBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<RetrofitViewModel>()

    private val adapter by lazy {
        ArticlesListAdapter(
            itemClick = {},
            longItemClick = {}
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRetrofitBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter

            recyclerView.addPaginationScrollListener(linearLayoutManager, ITEMS_TO_LOAD) {
                viewModel
            }

            viewModel.lceFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    binding.progressCircular.isVisible = it == LceState.Loading
                    when (it) {
                        is LceState.Content -> {
                            adapter.submitList(it.articles)
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                view,
                                it.throwable.message ?: "State_Error",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        LceState.Loading -> {

                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    companion object {
        const val ITEMS_TO_LOAD = 10
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}