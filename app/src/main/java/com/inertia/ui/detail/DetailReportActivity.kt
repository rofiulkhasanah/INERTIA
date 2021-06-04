package com.inertia.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.inertia.R
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.databinding.ActivityDetailReportBinding
import com.inertia.databinding.CrowdfundingAlertLayoutBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.login.LoginActivity
import com.inertia.utils.ViewModelFactory
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style

class DetailReportActivity : AppCompatActivity() {
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
        Mapbox.getInstance(this, resources.getString(R.string.mapbox_access_token))
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailReportViewModel::class.java]

        viewModel.setBencana(intent.getParcelableExtra(EXTRA_REPORT))
        showFab()
        user = viewModel.getUser()
        showDetailBencana()
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

    private fun showDetailBencana() {
        viewModel.getBencana().observe(this, {
            binding.contentDetail.tvIsiDeskripsi.text = it.kronologiBencana
            binding.contentDetail.tvKategori.text = it.jenisBencana
            binding.contentDetail.tvNamaBencana.text = it.namaBencana
            Glide.with(this@DetailReportActivity)
                .load(it.linkFoto)
                .into(binding.contentDetail.imgDetailLaporan)
            binding.contentDetail.tvLokasi.text = "${it.kota}, ${it.provinsi}"
            if (it.uriDonasi?.isNotEmpty() == true) {
                binding.contentDetail.crowdfundingLayout.visibility = View.VISIBLE
                binding.contentDetail.tvCrowdfunding.text = "Klik disini"
                binding.contentDetail.crowdfundingLayout.setOnClickListener { v ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.uriDonasi))
                    startActivity(intent)
                }
            }
            binding.contentDetail.maps.getMapAsync { map ->
                map.setStyle(Style.MAPBOX_STREETS) { style ->
                    val latitude = it.latitude ?: 0.00
                    val longitude = it.longitude ?: 0.00
                    val position = CameraPosition.Builder()
                        .target(LatLng(longitude, latitude))
                        .zoom(13.0)
                        .build()
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)
                    map.addOnMapClickListener {
                        // Create a Uri from an intent string. Use the result to create an Intent.
                        val gmmIntentUri = Uri.parse("geo:$longitude,$latitude")

                        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        // Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps")

                        // Attempt to start an activity that can handle the Intent
                        startActivity(mapIntent)

                        true
                    }
                }
            }
        })
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
                        detailBencana = it
                        showDetailBencana()
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
}