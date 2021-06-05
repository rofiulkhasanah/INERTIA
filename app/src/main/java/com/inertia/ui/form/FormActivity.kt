package com.inertia.ui.form

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.inertia.data.datasource.local.entity.BencanaEntity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.BencanaRequest
import com.inertia.databinding.ActivityFormBinding
import com.inertia.ui.detail.DetailReportActivity
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.data.source.remote.StatusResponse
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMG_URI = "extra_img_uri"
        const val EXTRA_FILE = "extra_file"
    }

    private lateinit var binding: ActivityFormBinding
    private lateinit var file: File

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
        file = intent.getSerializableExtra(EXTRA_FILE) as File
        imageUri?.path?.let { Log.d("FormActivity", it) }

        if (imageUri != null) {
            Glide.with(this).load(imageUri).into(binding.imgLaporan)
        }

        binding.formProgressbar.visibility = View.VISIBLE
        viewModel.getLocation(this).observe(this, { location ->
            binding.formProgressbar.visibility = View.GONE
            val latLong = "${location.latitude},${location.longitude}"
            binding.tvKoordinat.text = latLong
            binding.btnKirim.setOnClickListener {
                kirimForm(latLong)
            }
        })
    }

    private fun kirimForm(latLong: String){
        binding.apply {
            when {
                editNamaBencana.text?.isEmpty() == true -> {
                    editNamaBencana.error = "Kolom harus diisi"
                    editNamaBencana.requestFocus()
                    return
                }
                editDeksripsiLaporanBencana.text?.isEmpty() == true -> {
                    editDeksripsiLaporanBencana.error = "Kolom harus diisi"
                    editDeksripsiLaporanBencana.requestFocus()
                    return
                }
                user?.nomorWa == null -> {
                    Toast.makeText(this@FormActivity, "Anda Belum Login. Silakan login dahulu", Toast.LENGTH_SHORT).show()
                    return
                }
                else -> {
                    val nomorWa = user?.nomorWa ?: ""
                    val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault())
                    val waktuBencana = formatter.format(Date())

                    val request = BencanaRequest(
                        file,
                        editNamaBencana.text.toString(),
                        editDeksripsiLaporanBencana.text.toString(),
                        nomorWa,
                        waktuBencana,
                        latLong
                    )
                    formProgressbar.visibility = View.VISIBLE
                    viewModel.createLaporan(request).observe(this@FormActivity, {
                        when(it.status) {
                            StatusResponse.SUCCESS -> {
                                Toast.makeText(this@FormActivity, "Sukses mengirim laporan", Toast.LENGTH_SHORT).show()
                                val bencana = it.body.bencana
                                val alamat = it.body.address
                                if (bencana != null && alamat != null) {
                                    val latLongSplit = bencana.latLong?.split(",")
                                    val lat = latLongSplit?.get(0)?.toDouble()
                                    val long = latLongSplit?.get(1)?.toDouble()
                                    val entity = BencanaEntity(
                                        bencana.idAduan,
                                        bencana.judul,
                                        bencana.jenisBencana,
                                        bencana.kronologi,
                                        lat,
                                        long, null, bencana.waktuBencana,
                                        bencana.gambarUri, bencana.senderWaNumber, alamat.city, alamat.state, null)
                                    startActivity(Intent(this@FormActivity, DetailReportActivity::class.java).apply {
                                        putExtra(DetailReportActivity.EXTRA_REPORT, entity)
                                    })
                                }
                                finish()
                            }
                            StatusResponse.ERROR -> {
                                formProgressbar.visibility = View.GONE
                                Toast.makeText(this@FormActivity, "Gagal mengirim laporan: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })


                }
            }
        }
    }
}