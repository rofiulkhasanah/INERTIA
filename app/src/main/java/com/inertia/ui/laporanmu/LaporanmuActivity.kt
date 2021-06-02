package com.inertia.ui.laporanmu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.inertia.databinding.ActivityLaporanmuBinding
import com.inertia.ui.home.BencanaAdapter
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status

class LaporanmuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaporanmuBinding
    private lateinit var viewModel: LaporanmuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporanmuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LaporanmuViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        val adapter = BencanaAdapter()
        val layoutmanager = GridLayoutManager(this, 2)
        binding.rvLaporanmu.adapter = adapter
        binding.rvLaporanmu.layoutManager = layoutmanager
        val nomorWa = viewModel.getUser().nomorWa
        if (nomorWa != null) {
            viewModel.getBencanaByNomorWa(nomorWa).observe(this, {
                when(it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {
                            adapter.setData(it.data)
                        }
                        binding.laporanmuProgressbar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                        binding.laporanmuProgressbar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.laporanmuProgressbar.visibility = View.VISIBLE
                    }
                }
            })
        }

    }
}