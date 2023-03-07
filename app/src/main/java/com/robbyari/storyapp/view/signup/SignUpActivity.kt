package com.robbyari.storyapp.view.signup

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.robbyari.storyapp.R
import com.robbyari.storyapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        playAnimation()
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        signUpViewModel.isLoading.observe(this) { showLoading(it) }
        signUpViewModel.alertDialog.observe(this) { alertDialog(it) }
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.edRegisterName.error = getString(R.string.masukan_nama)
                }
                email.isEmpty() -> {
                    binding.edRegisterEmail.error = getString(R.string.masukan_email)
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = getString(R.string.masukan_password)
                }
                else -> {
                    signUpViewModel.registerUser(name, email, password)
                }
            }
        }
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun alertDialog(b: Boolean) {
        if (b) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.yeah))
                setMessage(getString(R.string.account_created))
                setPositiveButton(R.string.lanjut) { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgLogo, View.ROTATION, 360f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }

}