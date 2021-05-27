package com.inertia.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.inertia.R
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.databinding.ActivityDetailReportBinding
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.ViewModelFactory

class DetailReportActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_REPORT = "extra_report"
    }

    private lateinit var binding: ActivityDetailReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)

        val detailBencana = intent.getParcelableExtra<BencanaEntity>(EXTRA_REPORT)
        showDetailBencana(detailBencana)
    }

    private fun showDetailBencana(detailBencana: BencanaEntity?) {
        detailBencana?.let {
            binding.tvIsiDeskripsi.text = detailBencana.kronologiBencana
            binding.tvKategori.text = detailBencana.jenisBencana
            binding.tvNamaBencana.text = detailBencana.namaBencana
                Glide.with(this@DetailReportActivity)
                    .load(detailBencana.linkFoto)
                    .into(binding.imgDetailLaporan)

            binding.addFab.setOnClickListener {
            }

        }

    }
}