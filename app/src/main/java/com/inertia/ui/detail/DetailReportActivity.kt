package com.inertia.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.databinding.ActivityDetailReportBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.login.LoginActivity
import com.inertia.utils.ViewModelFactory

class DetailReportActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_REPORT = "extra_report"
    }

    private lateinit var binding: ActivityDetailReportBinding
    private lateinit var preferences: UserPreferences
    private lateinit var viewModel: DetailReportViewModel
    private var isFabVisible = false
    private var detailBencana: BencanaEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = UserPreferences(this)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailReportViewModel::class.java]
        
        detailBencana = intent.getParcelableExtra(EXTRA_REPORT)
        showFab()
        showDetailBencana(detailBencana)
    }

    private fun showFab() {
        binding.addFab.setOnClickListener {
            isFabVisible = if (isFabVisible) {
                binding.crowdfundingFab.hide()
                binding.assesmentFab.hide()
                false
            }else{
                binding.crowdfundingFab.show()
                binding.assesmentFab.show()
                true
            }
        }
        binding.crowdfundingFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailBencana?.linkFoto))
            startActivity(intent)
        }
        binding.assesmentFab.setOnClickListener {
            if (preferences.getUser().nomorWa != null) {
                val intent = Intent(this, AssessmentActivity::class.java)
                intent.putExtra(AssessmentActivity.DETAIL_BENCANA, detailBencana)
                intent.putExtra(AssessmentActivity.USER, preferences.getUser())
                startActivity(intent)
            }else{
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
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