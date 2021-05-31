package com.inertia.ui.form

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.inertia.R
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.databinding.ActivityFormBinding
import com.inertia.ui.main.MainActivity
import com.inertia.utils.ViewModelFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMG_URI = "extra_img_uri"
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var binding: ActivityFormBinding
    private var file: File? = null

    private lateinit var viewModel: FormViewModel
    private var user: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[FormViewModel::class.java]
        user = viewModel.getUser()

        val imageUri: Uri? = intent.getParcelableExtra(EXTRA_IMG_URI)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (imageUri != null) {
            Glide.with(this).load(imageUri).into(binding.imgLaporan)
            binding.btnKirim.setOnClickListener {
                kirimForm(imageUri)
            }
        }
    }

    private fun kirimForm(imageUri: Uri){
        binding.apply {
            if (editNamaBencana.text?.isEmpty() == true) {
                editNamaBencana.error = "Kolom harus diisi"
                editNamaBencana.requestFocus()
                return
            } else if (editDeksripsiLaporanBencana.text?.isEmpty() == true) {
                editDeksripsiLaporanBencana.error = "Kolom harus diisi"
                editDeksripsiLaporanBencana.requestFocus()
                return
            }else if(user?.nomorWa == null) {
                Toast.makeText(this@FormActivity, "Anda Belum Login. Silakan login dahulu", Toast.LENGTH_SHORT).show()
                return
            }else{
                val nomorWa = user?.nomorWa ?: ""

                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault())
                val waktuBencana = formatter.format(Date())

                val request = BencanaRequest(
                    imageUri,
                    editNamaBencana.text.toString(),
                    editDeksripsiLaporanBencana.text.toString(),
                    nomorWa,
                    waktuBencana,
                    getLocation()
                )
                viewModel.createLaporan(request)
                finish()
            }
        }
    }

    private fun getLocation(): String {
        val lastLoc = fusedLocationProviderClient.lastLocation
        var lat_long = ""

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
        lastLoc.addOnSuccessListener { location: Location? ->
            if (location != null){
                lat_long = "${location.latitude}, ${location.longitude}"
            }
        }
        return lat_long
    }
}