package com.inertia.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.user.IUserRepository
import com.inertia.databinding.ActivityLoginBinding
import com.inertia.ui.register.RegisterActivity
import com.inertia.ui.verification.VerificationActivity
import com.inertia.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.edtPhone.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                login()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        binding.apply {
            if (edtPhone.text?.isEmpty() == true) {
                edtPhone.error = "Kolom harus diisi"
                edtPhone.requestFocus()
                return
            }
            val phoneNumber = edtPhone.text.toString()
            progressBar2.visibility = View.VISIBLE
            viewModel.login(phoneNumber, object : IUserRepository.LoginCallback {
                override fun onLoginSuccessCallback(
                    userEntity: UserEntity,
                    verificationCode: String?,
                ) {
                    val intent = Intent(this@LoginActivity, VerificationActivity::class.java)
                    progressBar2.visibility = View.GONE
                    intent.putExtra(VerificationActivity.EXTRA_USER, userEntity)
                    intent.putExtra(VerificationActivity.EXTRA_CODE, verificationCode)
                    startActivity(intent)
                }
            })
        }
    }
}