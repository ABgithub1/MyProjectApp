package com.example.myprojectapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myprojectapp.databinding.FragmentHomeBinding
import com.example.myprojectapp.ui.newFragment.BlankFragment
import com.example.myprojectapp.ui.news.EverythingNewsFragment
import com.example.myprojectapp.ui.news.TopNewsFragment
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IndexOutOfBoundsException

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewPager.adapter = TabAdapter(this@HomeFragment)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                when(position){
                    0 -> tab.text = "Top news"
                    1 -> tab.text = "Everything news"
                    2 -> tab.text = "Saved news"
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TopNewsFragment()
            1 -> EverythingNewsFragment()
            2 -> BlankFragment()
            else -> throw IndexOutOfBoundsException("Wrong position $position")
        }
    }

}