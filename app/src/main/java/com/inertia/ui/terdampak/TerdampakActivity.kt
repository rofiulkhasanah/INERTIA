package com.inertia.ui.terdampak

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.inertia.data.datasource.local.entity.TerdampakEntity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.data.datasource.remote.api.InertiaService
import com.inertia.data.datasource.remote.response.TerdampakResponse
import com.inertia.databinding.ActivityTerdampakBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.detail.DetailReportViewModel
import com.inertia.ui.main.MainViewModel
import com.inertia.ui.profile.ProfileFragment
import com.inertia.ui.verification.VerificationViewModel
import com.inertia.utils.ViewModelFactory
import retrofit2.Call
import retrofit2.Response

class TerdampakActivity : AppCompatActivity() {

    companion object{
        const val USER = "user"
    }

    private lateinit var binding: ActivityTerdampakBinding
    private var terdampakResponseItem : ArrayList<TerdampakResponse> = ArrayList()
    private lateinit var nomor_wa: String
    private lateinit var preferences: UserPreferences
    private lateinit var viewModel: MainViewModel
    private var user: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerdampakBinding.inflate(layoutInflater)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        user = viewModel.getUser()
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        nomor_wa = user?.nomorWa.toString()

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
                    binding.progressBar.visibility = if(data.isNotEmpty()) View.GONE else View.VISIBLE
                    binding.progressBar.visibility = if(data.isEmpty()) View.GONE else View.VISIBLE
                    binding.tvEmpty.visibility = if(data.isEmpty()) View.VISIBLE else View.GONE
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