package com.inertia.ui.home

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.inertia.R
import com.inertia.databinding.FragmentHomeBinding
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.LocationProvider
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status
import java.lang.ClassCastException


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setDropdownItem()
        getBencanaData()
        getCuaca()
    }

    private fun getBencanaData() {
        viewModel.getAllBencana().observe(viewLifecycleOwner, {
            val adapter = BencanaAdapter()
            val layoutManager = GridLayoutManager(context, 2)
            binding.rvBencana.adapter = adapter
            binding.rvBencana.layoutManager = layoutManager
            when(it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        Log.d("HomeFragment", "Jumlah Data: ${it.data.size}")
                        adapter.setData(it.data)
                    }
                    binding.bencanaLoading.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.bencanaLoading.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    if (it.data != null) {
                        adapter.setData(it.data)
                    }
                    binding.bencanaLoading.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setDropdownItem() {
        val sortFilter = arrayOf("Baru baru ini", "Terjadi didekatmu", "Apalagi ya")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, sortFilter)
        binding.spinner.adapter = arrayAdapter
    }

    private fun getCuaca() {
        viewModel.getLocation(requireActivity()).observe(viewLifecycleOwner, { location ->
            viewModel.getCuaca(location.latitude,location.longitude).observe(viewLifecycleOwner, {
                with(binding) {
                    layoutWeather.tvTemp.text = getString(R.string.temp, it.temp)
                    layoutWeather.tvCloud.text = it.cloud
                    layoutWeather.tvWind.text = getString(R.string.wind, it.wind)
                    layoutWeather.tvHumidity.text = getString(R.string.humidity, it.humidity)
                }
            })
        })
    }



    companion object {
        const val REQUEST_CHECK_SETTINGS = 10
    }
}