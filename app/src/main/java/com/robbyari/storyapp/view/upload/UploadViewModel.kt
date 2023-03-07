package com.robbyari.storyapp.view.upload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.remote.response.FileUploadResponse
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alertDialog = MutableLiveData<Boolean>()
    val alertDialog: LiveData<Boolean> = _alertDialog

    fun uploadStory(imageMultipart: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        val token = runBlocking { pref.getToken.first() }
        val client =
            ApiConfig.getApiService().uploadStory("Bearer $token", imageMultipart, description)
        client.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _alertDialog.value = true
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.e("MainViewModel", t.message!!)
            }
        })
    }

}