package com.inertia.ui.terdampak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inertia.R
import com.inertia.databinding.ActivityTerdampakBinding

class TerdampakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTerdampakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerdampakBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}