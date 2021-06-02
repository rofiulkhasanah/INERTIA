package com.inertia.ui.terdampak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.inertia.R
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.datasource.remote.response.TerdampakResponse
import com.inertia.databinding.ActivityTerdampakBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status
import retrofit2.Call
import retrofit2.Response

class TerdampakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTerdampakBinding
    private var terdampakResponseItem : ArrayList<TerdampakResponse> = ArrayList()

    companion object{
        const val USER = "user"
    }
    private lateinit var nomor_wa: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerdampakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        val detailUser = intent.getParcelableExtra<UserEntity>(AssessmentActivity.USER)

        nomor_wa = detailUser?.nomorWa.toString()
        val terdampakAdapter = TerdampakAdapter()
        InertiaService().getTerdampak().getAllTerdampak(nomor_wa).enqueue(object :
            retrofit2.Callback<List<TerdampakResponse>> {
            override fun onResponse(
                call: Call<List<TerdampakResponse>>,
                response: Response<List<TerdampakResponse>>
            ) {

                val data = response.body()
                if (data != null) {
                    terdampakResponseItem = response.body() as ArrayList<TerdampakResponse>
                    val data : ArrayList<TerdampakEntity> = ArrayList()
                    terdampakResponseItem.forEach {
                        val s = TerdampakEntity(
                            it.idKasus,
                            it.nomorWa,
                            it.idBencana,
                            it.idSub,
                            it.nama,
                            it.alamat,
                            it.provinsi,
                            it.kota,
                            it.tanggal,
                            it.namaBencana
                        )
                        data.add(s)
                    }
                    terdampakAdapter.setData(data)
                }
            }

            override fun onFailure(call: Call<List<TerdampakResponse>>, t: Throwable) {
                Log.e("Gagal", "Error: ${t.message}")
            }
        })

        with(binding.rvTerdampak){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = terdampakAdapter
        }
    }
}