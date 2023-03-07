package com.robbyari.storyapp.view.login

import android.util.Log
import androidx.lifecycle.*
import com.robbyari.storyapp.repository.local.datastore.LoginResponse
import com.robbyari.storyapp.repository.local.datastore.LoginResult
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.repository.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _user = MutableLiveData<LoginResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _alertDialog = MutableLiveData<Boolean>()
    val alertDialog: LiveData<Boolean> = _alertDialog

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                    _alertDialog.value = true
                    viewModelScope.launch {
                        pref.saveUser(response.body()!!.loginResult)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

}