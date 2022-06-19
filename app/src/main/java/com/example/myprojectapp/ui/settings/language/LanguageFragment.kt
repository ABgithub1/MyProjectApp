package com.example.myprojectapp.ui.settings.language

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myprojectapp.R
import com.example.myprojectapp.databinding.FragmentLanguageBinding
import com.example.myprojectapp.databinding.FragmentNightModeBinding
import com.example.myprojectapp.model.settings.Language
import org.koin.androidx.viewmodel.ext.android.viewModel

class LanguageFragment : Fragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<LanguageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLanguageBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            when (viewModel.selectedLanguage) {
                Language.EN -> buttonEn
                Language.RU -> buttonRu
            }.isChecked = true

            radioGroup.setOnCheckedChangeListener { _, buttonId ->
                val language = when (buttonId) {
                    buttonEn.id -> Language.EN
                    buttonRu.id -> Language.RU
                    else -> error("incorrect buttonId $buttonId")
                }
                viewModel.selectedLanguage = language

                activity?.recreate()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}