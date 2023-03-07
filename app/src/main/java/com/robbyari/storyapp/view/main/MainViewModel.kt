package com.robbyari.storyapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.robbyari.storyapp.repository.local.datastore.LoginResult
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.remote.response.Story
import com.robbyari.storyapp.repository.remote.response.StoryResponse
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

}