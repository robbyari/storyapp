package com.robbyari.storyapp.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.robbyari.storyapp.repository.data.StoryRepository
import com.robbyari.storyapp.repository.remote.response.Story

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStory(): LiveData<PagingData<Story>> =
        storyRepository.getStory().cachedIn(viewModelScope)

}