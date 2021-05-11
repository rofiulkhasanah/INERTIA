package com.inertia.ui.form

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.inertia.R
import com.inertia.databinding.ActivityFormBinding
import com.inertia.ui.main.MainActivity

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
            finish()
        }
    }
}