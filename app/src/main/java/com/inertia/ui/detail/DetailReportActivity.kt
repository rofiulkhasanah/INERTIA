package com.inertia.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
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

class DetailReportActivity : AppCompatActivity() {
    companion object{
        const val BENCANA = "bencana"
    }

    private lateinit var binding: ActivityDetailReportBinding
    private lateinit var viewModel: DetailReportViewModel
    private lateinit var user: UserEntity
    private var isFabVisible = false
    private var detailBencana: BencanaEntity? = null
    private lateinit var mutableDetailBencana: LiveData<BencanaEntity>
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailReportViewModel::class.java]
        viewModel.setBencana(intent.getParcelableExtra(BENCANA))
        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        user = viewModel.getUser()

        showFab()
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
            mutableDetailBencana = viewModel.getBencana()
            if (user.nomorWa != null) {
                val intent = Intent(this, AssessmentActivity::class.java)
                intent.putExtra(AssessmentActivity.BENCANA, mutableDetailBencana.value)
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
            binding.contentDetail.tvLokasi.text = resources.getString(R.string.kota_provinsi, it.kota, it.provinsi)
            if (it.uriDonasi?.isNotEmpty() == true) {
                binding.contentDetail.crowdfundingLayout.visibility = View.VISIBLE
                binding.contentDetail.crowdfundingLayout.setOnClickListener { v ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.uriDonasi))
                    startActivity(intent)
                }
            }
            mapFragment.getMapAsync { map ->
                val latitude = it.latitude ?: 0.00
                val longitude = it.longitude ?: 0.00
                val latLong = LatLng(longitude, latitude)
                map.addMarker(MarkerOptions().position(latLong))
                map.moveCamera(CameraUpdateFactory.newLatLng(latLong))
                map.moveCamera(CameraUpdateFactory.zoomTo(13.0f))
                map.setOnMapClickListener {
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    val gmmIntentUri = Uri.parse("geo:$longitude,$latitude")

                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    // Make the Intent explicit by setting the Google Maps package
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // Attempt to start an activity that can handle the Intent
                    startActivity(mapIntent)
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