package com.inertia.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.databinding.ActivityLoginBinding
import com.inertia.ui.register.RegisterActivity
import com.inertia.ui.verification.VerificationActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvDaftar.setOnClickListener {
            daftar()
        }
    }

    private fun daftar() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val userEntity = UserEntity()
        binding.apply{
            if(edtPhone.text?.isEmpty() == true){
                edtPhone.error = "Kolom harus diisi"
                edtPhone.requestFocus()
                return
            }
            userEntity.phoneNumber = edtPhone.text.toString()
        }

        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra(VerificationActivity.EXTRA_USER, userEntity)
        intent.putExtra(VerificationActivity.EXTRA_CODE, "794329")
        startActivity(intent)
    }
}