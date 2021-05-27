package com.inertia.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.inertia.R
import com.inertia.databinding.FragmentHomeBinding
import com.inertia.ui.detail.DetailReportActivity
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude: Double = 0.00
    private var longitude: Double = 0.00

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
            viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setDropdownItem()
        getBencanaData()
        getLocation()

    }

    private fun getBencanaData() {
        val adapter = BencanaAdapter()
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvBencana.adapter = adapter
        binding.rvBencana.layoutManager = layoutManager
        viewModel.getAllBencana().observe(viewLifecycleOwner, {
            when(it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
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

    private fun getLocation() {
        val lastLoc = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
                return
            }
        lastLoc.addOnSuccessListener { location: Location? ->
            if (location != null){
                latitude = location.latitude
                longitude = location.longitude
                getCuaca()
            }
        }
    }

    fun getCuaca() {
        viewModel.getCuaca(latitude,longitude).observe(viewLifecycleOwner, {
            with(binding) {
                layoutWeather.tvTemp.text = getString(R.string.temp, it.temp)
                layoutWeather.tvCloud.text = it.cloud
                layoutWeather.tvWind.text = getString(R.string.wind, it.wind)
                layoutWeather.tvHumidity.text = getString(R.string.humidity, it.humidity)
            }
        })
    }
}