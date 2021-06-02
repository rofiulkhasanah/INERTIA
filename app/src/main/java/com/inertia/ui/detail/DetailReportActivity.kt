package com.inertia.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.databinding.ActivityDetailReportBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.login.LoginActivity

class DetailReportActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_REPORT = "extra_report"
    }

    private lateinit var binding: ActivityDetailReportBinding
    private lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = UserPreferences(this)

        val detailBencana = intent.getParcelableExtra<BencanaEntity>(EXTRA_REPORT)

        binding.addFab.setOnClickListener {
            if (preferences.getUser().nomorWa != null) {
                val intent = Intent(this, AssessmentActivity::class.java)
                intent.putExtra(AssessmentActivity.DETAIL_BENCANA, detailBencana)
                intent.putExtra(AssessmentActivity.USER, preferences.getUser())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
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
        }
    }
}