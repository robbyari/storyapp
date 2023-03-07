package com.robbyari.storyapp.view.signup

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robbyari.storyapp.repository.remote.response.RegisterResponse
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val _story = MutableLiveData<RegisterResponse>()
    val story: LiveData<RegisterResponse> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alertDialog = MutableLiveData<Boolean>()
    val alertDialog: LiveData<Boolean> = _alertDialog

    fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true
        _alertDialog.value = false
        val client = ApiConfig.getApiService().registerUser(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _story.value = response.body()
                    _alertDialog.value = true
                } else {
                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("SignUpViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

}