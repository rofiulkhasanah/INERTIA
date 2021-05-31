package com.inertia.ui.terdampak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.inertia.R
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.local.preference.UserPreferences
import com.inertia.databinding.ActivityTerdampakBinding
import com.inertia.ui.assessment.AssessmentActivity
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status

class TerdampakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTerdampakBinding
    private lateinit var viewModel: TerdampakViewModel

    companion object{
        const val USER = "user"
    }
    private lateinit var nomor_wa: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerdampakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TerdampakViewModel::class.java]

        getData()
    }

    private fun getData() {
        val detailUser = intent.getParcelableExtra<UserEntity>(AssessmentActivity.USER)

        nomor_wa = detailUser?.nomorWa.toString()

        val terdampakAdapter = TerdampakAdapter()

        viewModel.getAllTerdampak(nomor_wa).observe(this, {
            when(it.status) {
                Status.SUCCESS -> {
                    if(it.data != null) {
                        terdampakAdapter.setData(it.data)
                    }
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    if(it.data != null) {
                        terdampakAdapter.setData(it.data)
                    }
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        with(binding.rvTerdampak){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = terdampakAdapter
        }
    }
}