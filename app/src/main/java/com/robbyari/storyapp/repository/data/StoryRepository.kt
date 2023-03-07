package com.robbyari.storyapp.repository.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.local.room.StoryDatabase
import com.robbyari.storyapp.repository.remote.response.Story
import com.robbyari.storyapp.repository.remote.response.StoryResponse
import com.robbyari.storyapp.repository.remote.retrofit.ApiService

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val token: UserPreference) {

    fun getStory(): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}