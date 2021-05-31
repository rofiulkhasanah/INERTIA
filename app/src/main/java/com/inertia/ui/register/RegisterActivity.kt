package com.inertia.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inertia.R
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.repository.user.IUserRepository
import com.inertia.databinding.ActivityRegisterBinding
import com.inertia.ui.login.LoginActivity
import com.inertia.ui.verification.VerificationActivity
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.data.source.remote.StatusResponse

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        with(binding) {
            progressBar.visibility = View.VISIBLE
            if (edtName.text?.isEmpty() == true) {
                edtName.error = "Kolom harus diisi"
                edtName.requestFocus()
                return
            } else if (edtRegPhone.text?.isEmpty() == true) {
                edtRegPhone.error = "Kolom harus diisi"
                edtRegPhone.requestFocus()
                return
            }else if (editAlamat.text?.isEmpty() == true) {
                editAlamat.error = "Kolom harus diisi"
                editAlamat.requestFocus()
                return
            }
            val gender = when(rgJenisKelamin.checkedRadioButtonId) {
                R.id.gender_laki -> "Laki - Laki"
                R.id.gender_perempuan -> "Perempuan"
                else -> return
            }

            val nama = edtName.text.toString()
            val alamat = editAlamat.text.toString()
            val nomorWa = edtRegPhone.text.toString()

            val request = RegisterRequest(
                nama, alamat, gender, nomorWa, "0"
            )
            progressBar.visibility = View.VISIBLE
            viewModel.register(request).observe(this@RegisterActivity, {
                when(it.status) {
                    StatusResponse.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.putExtra(LoginActivity.EXTRA_USER, it.body)
                        startActivity(intent)
                    }
                    StatusResponse.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }
    }
}