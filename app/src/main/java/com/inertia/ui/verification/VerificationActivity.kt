package com.inertia.ui.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.inertia.R
import com.inertia.databinding.ActivityVerificationBinding
import com.inertia.ui.main.MainActivity

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifCode1.addTextChangedListener {
            if (it.toString() != "") {
                binding.verifCode2.requestFocus()
            }
        }
        binding.verifCode2.addTextChangedListener {
            if (it.toString() != "") {
                binding.verifCode3.requestFocus()
            } else {
                binding.verifCode1.requestFocus()
            }
        }
        binding.verifCode3.addTextChangedListener {
            if (it.toString() != "") {
                binding.verifCode4.requestFocus()
            } else {
                binding.verifCode2.requestFocus()
            }
        }
        binding.verifCode4.addTextChangedListener {
            if (it.toString() != "") {
                binding.verifCode5.requestFocus()
            } else {
                binding.verifCode3.requestFocus()
            }
        }
        binding.verifCode5.addTextChangedListener {
            if (it.toString() != "") {
                binding.verifCode6.requestFocus()
            } else {
                binding.verifCode4.requestFocus()
            }
        }
        binding.verifCode6.addTextChangedListener { Intent(this, MainActivity::class.java) }


        binding.btnVerify.setOnClickListener {
            if (it.toString() != "") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                binding.verifCode5.requestFocus()
            }
        }
    }
}