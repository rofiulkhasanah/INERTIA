package com.inertia.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.inertia.R
import com.inertia.data.datasource.remote.response.WeatherResponse
import com.inertia.databinding.FragmentHomeBinding
import com.inertia.ui.main.MainViewModel
import com.inertia.utils.ViewModelFactory
import com.mirfanrafif.kicksfilm.vo.Status

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    var mLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setWeather()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = WeatherResponse(24.08, 95, 0.61, 95)
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setDropdownItem()
        getBencanaData()
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

    private fun setWeather() {
        val lastLoc = fusedLocationProviderClient.lastLocation
        var latitude: Double
        var longitude: Double

        if(context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED &&
            context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
                return
        }
        lastLoc.addOnSuccessListener { location: Location? ->
            mLocation = location
            if (location != null){
                latitude = location.latitude
                longitude = location.longitude
                Toast.makeText(context,"latlon: ${latitude} dan ${longitude}", Toast.LENGTH_SHORT).show()
//                Log.e("latlon", "${latitude} dan ${longitude}")
                viewModel.getCuaca(latitude.toString(),longitude.toString()).observe(viewLifecycleOwner, {
                    with(binding) {
                        layoutWeather.tvTemp.text = it.temp.toString()
                        layoutWeather.tvCloud.text = it.cloud.toString()
                        layoutWeather.tvWind.text = it.wind.toString()
                        layoutWeather.tvHumidity.text = it.humidity.toString()
                    }
                    Toast.makeText(context, "${it.temp}, ${it.cloud}, ${it.wind}, ${it.humidity}", Toast.LENGTH_SHORT).show()
                })
            }


        }



    }
}