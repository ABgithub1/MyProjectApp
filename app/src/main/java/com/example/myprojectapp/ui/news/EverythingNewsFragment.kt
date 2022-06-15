package com.example.myprojectapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myprojectapp.databinding.FragmentEverythingNewsBinding

class EverythingNewsFragment : Fragment() {
    private var _binding: FragmentEverythingNewsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEverythingNewsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}