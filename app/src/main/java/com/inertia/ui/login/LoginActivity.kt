package com.inertia.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inertia.R
import com.inertia.databinding.ActivityLoginBinding
import com.inertia.ui.main.MainActivity
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
        binding.apply{
            if(edtPhone.text?.isEmpty() == true){
                edtPhone.error = "Kolom harus diisi"
                edtPhone.requestFocus()
                return
            }
        }
        val intent = Intent(this, VerificationActivity::class.java)
        startActivity(intent)
    }
}