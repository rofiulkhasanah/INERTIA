package com.inertia.ui.form

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inertia.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMG_URI = "extra_img_uri"
    }

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri: Uri? = intent.getParcelableExtra(EXTRA_IMG_URI)
        if (imageUri != null) {
            Glide.with(this).load(imageUri).into(binding.imgLaporan)
        }

        binding.btnKirim.setOnClickListener {
            kirimForm()
        }
    }

    private fun kirimForm() {
        binding.apply {
            if (editNamaBencana.text?.isEmpty() == true) {
                editNamaBencana.error = "Kolom harus diisi"
                editNamaBencana.requestFocus()
                return
            } else if (editDeksripsiLaporanBencana.text?.isEmpty() == true) {
                editDeksripsiLaporanBencana.error = "Kolom harus diisi"
                editDeksripsiLaporanBencana.requestFocus()
                return
            } else {
                finish()
            }
        }
    }
}