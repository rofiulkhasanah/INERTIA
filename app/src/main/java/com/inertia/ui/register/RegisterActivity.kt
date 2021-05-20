package com.inertia.ui.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.inertia.R
import com.inertia.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        binding.apply {
            if (edtName.text?.isEmpty() == true) {
                edtName.error = "Kolom harus diisi"
                edtName.requestFocus()
                return
            } else if (edtRegPhone.text?.isEmpty() == true) {
                edtRegPhone.error = "Kolom harus diisi"
                edtRegPhone.requestFocus()
                return
            }else if (edtCity.text?.isEmpty() == true) {
                edtCity.error = "Kolom harus diisi"
                edtCity.requestFocus()
                return
            }else if (edtProvinsi.text?.isEmpty() == true) {
                edtProvinsi.error = "Kolom harus diisi"
                edtProvinsi.requestFocus()
                return
            }
        }
    }
}