package com.inertia.ui.detailassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inertia.R
import com.inertia.databinding.ActivityAssessmentBinding
import com.inertia.databinding.ActivityDetailAssessmentBinding

class DetailAssessmentActivity : AppCompatActivity() {
    companion object {
        const val DETAIL_PENILAIAN = "detail_penilaian"
    }

    private lateinit var binding: ActivityDetailAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}