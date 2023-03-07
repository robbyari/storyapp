package com.robbyari.storyapp.view.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.robbyari.storyapp.adapter.LoadingStateAdapter
import com.robbyari.storyapp.adapter.StoryAdapter
import com.robbyari.storyapp.databinding.FragmentStoryBinding
import com.robbyari.storyapp.helper.StoryModelFactory

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel by viewModels<StoryViewModel> {
        StoryModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }

    private fun getData() {
        val adapter = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(context)
        binding.rvStory.setHasFixedSize(true)

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storyViewModel.getStory().observe(viewLifecycleOwner) {
            try {
                adapter.submitData(lifecycle, it)
            } catch (exception: Exception) {
                Log.d(TAG, "$exception")
            }

        }
    }

    companion object {
        const val TAG = "cek cek story fragment"

    }

}