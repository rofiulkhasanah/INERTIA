package com.inertia.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.inertia.R
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.databinding.ActivityRegisterBinding
import com.inertia.ui.verification.VerificationActivity
import com.inertia.utils.DataMapper
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

        setDropdownItem()
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register(){
        with(binding) {
            progressBar.visibility = View.VISIBLE
            when {
                edtName.text?.isEmpty() == true -> {
                    edtName.error = "Kolom harus diisi"
                    edtName.requestFocus()
                    return
                }
                edtRegPhone.text?.isEmpty() == true -> {
                    edtRegPhone.error = "Kolom harus diisi"
                    edtRegPhone.requestFocus()
                    return
                }
                editAlamat.text?.isEmpty() == true -> {
                    editAlamat.error = "Kolom harus diisi"
                    editAlamat.requestFocus()
                    return
                }
                else -> {
                    val gender = when (rgJenisKelamin.checkedRadioButtonId) {
                        R.id.gender_laki -> "Laki - Laki"
                        R.id.gender_perempuan -> "Perempuan"
                        else -> return
                    }

                    val nama = edtName.text.toString()
                    val alamat = editAlamat.text.toString()
                    val nomorWa = DataMapper.getValidNumber(edtRegPhone.text.toString())
                    var jenisPengguna : String? = null
                    if (binding.spinner.selectedItemPosition == 0){
                        jenisPengguna = "0"
                    }else if(binding.spinner.selectedItemPosition == 1){
                        jenisPengguna = "1"
                    }
                    val request = RegisterRequest(
                        nama, alamat, gender, nomorWa, jenisPengguna.toString()
                    )
                    progressBar.visibility = View.VISIBLE
                    viewModel.register(request).observe(this@RegisterActivity, {
                        when (it.status) {
                            StatusResponse.SUCCESS -> {
                                progressBar.visibility = View.GONE
                                val intent =
                                    Intent(this@RegisterActivity, VerificationActivity::class.java)
                                intent.putExtra(
                                    VerificationActivity.EXTRA_USER,
                                    DataMapper.mapRegisterResponseToUserEntity(it.body)
                                )
                                intent.putExtra(VerificationActivity.EXTRA_CODE, it.body.token)
                                startActivity(intent)
                            }
                            StatusResponse.ERROR -> {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@RegisterActivity,
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }

        }
    }

    private fun setDropdownItem() {
        val sortFilter = arrayOf("Pengguna Biasa", "Penanggung Jawab")
        val arrayAdapter = ArrayAdapter(this, R.layout.spinner_item, sortFilter)
        binding.spinner.adapter = arrayAdapter
    }
}