package com.example.myprojectapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myprojectapp.databinding.FragmentSettingsBinding
import com.example.myprojectapp.databinding.FragmentTopNewsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSettingsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            themeButton.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToNightModeFragment())
            }
            languageButton.setOnClickListener {
                findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToLanguageFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}