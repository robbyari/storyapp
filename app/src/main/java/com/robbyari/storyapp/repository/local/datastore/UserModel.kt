package com.robbyari.storyapp.repository.local.datastore

data class LoginResult(

    val userId: String,
    val name: String,
    val token: String,
    val isLogin: Boolean

)

data class LoginResponse(

    val loginResult: LoginResult

)

