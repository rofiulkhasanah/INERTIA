package com.inertia.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.user.IUserRepository
import com.inertia.databinding.ActivityLoginBinding
import com.inertia.ui.register.RegisterActivity
import com.inertia.ui.verification.VerificationActivity
import com.inertia.utils.DataMapper
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.data.source.remote.StatusResponse

class LoginActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserEntity>(EXTRA_USER)

        if (user != null) {
            binding.edtPhone.setText(user.nomorWa)
        }

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
        binding.apply{
            if(edtPhone.text?.isEmpty() == true){
                edtPhone.error = "Kolom harus diisi"
                edtPhone.requestFocus()
                return
            }
            val phoneNumber = DataMapper.getValidNumber(edtPhone.text.toString())

            progressBar2.visibility = View.VISIBLE
            Log.d("Login", phoneNumber)
            viewModel.login(phoneNumber).observe(this@LoginActivity, {
                when(it.status) {
                    StatusResponse.SUCCESS -> {
                        val intent = Intent(this@LoginActivity, VerificationActivity::class.java)
                        progressBar2.visibility = View.GONE
                        intent.putExtra(VerificationActivity.EXTRA_USER, DataMapper.mapLoginResponseToUserEntity(it.body))
                        intent.putExtra(VerificationActivity.EXTRA_CODE, it.body.token)
                        startActivity(intent)
                    }
                    StatusResponse.ERROR -> {
                        progressBar2.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}