package com.example.myprojectapp.ui.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectapp.adapter.ArticlesListAdapter
import com.example.myprojectapp.databinding.FragmentRetrofitBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import kotlinx.coroutines.flow.launchIn
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

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getTopNews().fold(
                    onSuccess = {
                        adapter.submitList(it)
                    },
                    onFailure = {
                        Snackbar.make(view, it.message + "Причина: " + it.cause, Snackbar.LENGTH_LONG).show()
                    }
                )
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}