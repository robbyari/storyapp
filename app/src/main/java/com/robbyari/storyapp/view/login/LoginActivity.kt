package com.robbyari.storyapp.view.login

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.robbyari.storyapp.R
import com.robbyari.storyapp.databinding.ActivityLoginBinding
import com.robbyari.storyapp.helper.ViewModelFactory
import com.robbyari.storyapp.repository.local.datastore.LoginResult
import com.robbyari.storyapp.repository.local.datastore.UserPreference
import com.robbyari.storyapp.view.main.MainActivity
import com.robbyari.storyapp.view.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginResult: LoginResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        playAnimation()
        setupViewModel()
        setupAction()
    }


    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            this.loginResult = user
        }

        loginViewModel.isLoading.observe(this) { showLoading(it) }

        loginViewModel.alertDialog.observe(this) { alertDialog(it) }
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.edLoginEmail.error = getString(R.string.masukan_email)
                }
                password.isEmpty() -> {
                    binding.edLoginPassword.error = getString(R.string.masukan_password)
                }
                else -> {
                    loginViewModel.loginUser(email, password)
                    loginViewModel.login()
                }
            }
        }
    }

    private fun alertDialog(b: Boolean) {
        if (b) {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.yeah)
                setMessage(getString(R.string.successLogin))
                setPositiveButton(getString(R.string.lanjut)) { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgLogo, View.ROTATION, 360f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }

}