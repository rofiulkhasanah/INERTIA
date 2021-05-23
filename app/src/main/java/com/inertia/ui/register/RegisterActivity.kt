package com.inertia.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inertia.data.datasource.local.entity.UserEntity
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
        with(binding) {
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

            val user = UserEntity()
            user.name = edtName.text.toString()
            user.phoneNumber = edtRegPhone.text.toString()
            user.kota = edtCity.text.toString()
            user.provinsi = edtProvinsi.text.toString()

        }
    }
}