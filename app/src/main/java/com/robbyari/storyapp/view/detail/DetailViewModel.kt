package com.robbyari.storyapp.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.remote.response.DetailStoryResponse
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: UserPreference) : ViewModel() {

    private val _detail = MutableLiveData<DetailStoryResponse>()
    val detail: LiveData<DetailStoryResponse> = _detail

    fun getStoryDetail(id: String) {
        val token = runBlocking { pref.getToken.first() }
        val client = ApiConfig.getApiService().getStoryDetail("Bearer $token", id)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _detail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

}