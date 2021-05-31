package com.inertia.ui.detailassessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inertia.R
import com.inertia.data.datasource.local.entity.PenilaianEntity
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.datasource.remote.response.PenilaianResponse
import com.inertia.databinding.ActivityAssessmentBinding
import com.inertia.databinding.ActivityDetailAssessmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAssessmentActivity : AppCompatActivity() {
    companion object {
        const val DETAIL_PENILAIAN = "detail_penilaian"
    }

    private lateinit var binding: ActivityDetailAssessmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
    }

    private fun initData() {
        InertiaService().getPenilaian().getPenilaian().enqueue(object :
            Callback<PenilaianResponse> {
            override fun onResponse(
                call: Call<PenilaianResponse>,
                response: Response<PenilaianResponse>
            ) {

                val data = response.body()
                println(data)
                if (data != null) {

                    binding.tvNama.text = data.nama
                    binding.tvAlamat.text = data.alamat
                    binding.tvHasil.text = data.namaAlternatif
                    binding.tvJenisbencana.text = data.nmBencana
                    binding.tvKota.text = data.kota
                    binding.tvProvinsi.text = data.provinsi
                }
            }

            override fun onFailure(call: Call<PenilaianResponse>, t: Throwable) {
                println("Gagal dikirim")
            }
        })
    }

}