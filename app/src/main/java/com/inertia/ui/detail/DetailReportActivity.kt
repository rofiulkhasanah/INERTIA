package com.inertia.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.inertia.R
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.databinding.ActivityDetailReportBinding
import com.inertia.databinding.CrowdfundingAlertLayoutBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.login.LoginActivity
import com.inertia.utils.ViewModelFactory

class DetailReportActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object{
        const val EXTRA_REPORT = "extra_report"
    }

    private lateinit var binding: ActivityDetailReportBinding
    private lateinit var viewModel: DetailReportViewModel
    private lateinit var user: UserEntity
    private var isFabVisible = false
    private var detailBencana: BencanaEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailReportViewModel::class.java]

        detailBencana = intent.getParcelableExtra(EXTRA_REPORT)
        showFab()
        user = viewModel.getUser()
        showDetailBencana(detailBencana)
        prepareMap()
    }

    private fun prepareMap() {
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun showFab() {
        binding.addFab.setOnClickListener {
            isFabVisible = if (isFabVisible) {
                binding.crowdfundingFab.hide()
                binding.assesmentFab.hide()
                false
            }else{
                if (user.jenisPengguna.equals("1")) binding.crowdfundingFab.show()
                binding.assesmentFab.show()
                true
            }
        }
        binding.crowdfundingFab.setOnClickListener {
            showAlertDialog()
        }
        binding.assesmentFab.setOnClickListener {
            if (user.nomorWa != null) {
                val intent = Intent(this, AssessmentActivity::class.java)
                intent.putExtra(AssessmentActivity.DETAIL_BENCANA, detailBencana)
                intent.putExtra(AssessmentActivity.USER, user)
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
            binding.tvLokasi.text = "${detailBencana.kota}, ${detailBencana.provinsi}"
            if (detailBencana.uriDonasi?.isNotEmpty() == true) {
                binding.crowdfundingLayout.visibility = View.VISIBLE
                binding.tvCrowdfunding.text = "Klik disini"
                binding.crowdfundingLayout.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailBencana.uriDonasi))
                    startActivity(intent)
                }
            }


        }
    }

    private fun showAlertDialog() {
        val alertBinding = CrowdfundingAlertLayoutBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setView(alertBinding.root)
            .setPositiveButton("Simpan") { dialog, which ->
                val donasiUri = alertBinding.uriDonasi.text.toString()
                val id = detailBencana?.id
                if (id != null) {
                    viewModel.updateBencana(id, donasiUri).observe(this, {
                        showDetailBencana(it)
                    })
                }
//                binding.crowdfundingLayout.visibility = View.VISIBLE
            }
            .setNegativeButton("Batal") {dialog, _ ->
                dialog.cancel()
            }

        val alert = builder.create()
        alert.show()
    }

    override fun onMapReady(p0: GoogleMap) {
        val latitude = detailBencana?.latitude
        val longitude = detailBencana?.longitude
        if (latitude != null && longitude != null) {
            val latLng = LatLng(latitude, longitude)
            p0.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Lokasi")
            )
        }
    }
}