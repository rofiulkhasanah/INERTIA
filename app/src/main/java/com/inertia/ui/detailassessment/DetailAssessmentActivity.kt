package com.inertia.ui.detailassessment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.datasource.remote.response.PenilaianResponse
import com.inertia.databinding.ActivityDetailAssessmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAssessmentActivity : AppCompatActivity() {

    companion object {
        const val idKasus = "idKasus"
    }

    private lateinit var binding: ActivityDetailAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idKasus = intent.getStringExtra(idKasus).toString()

        InertiaService().getPenilaian().getPenilaian(
            idKasus = idKasus
        ).enqueue(object :
            Callback<PenilaianResponse> {
            override fun onResponse(
                call: Call<PenilaianResponse>,
                response: Response<PenilaianResponse>,
            ) {

                val data = response.body()
                if (data != null) {
                    binding.tvNama.text = data.nama
                    binding.tvAlamat.text = data.alamat
                    binding.tvProvinsi.text = data.provinsi
                    binding.tvKota.text = data.kota
                    binding.tvJenisbencana.text = data.nmBencana
                    binding.tvHasil.text = data.namaAlternatif
                }
            }

            override fun onFailure(call: Call<PenilaianResponse>, t: Throwable) {
                println("Gagal dikirim")
            }
        })

    }
}