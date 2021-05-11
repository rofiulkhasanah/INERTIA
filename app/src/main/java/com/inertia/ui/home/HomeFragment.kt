package com.inertia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.inertia.R
import com.inertia.databinding.FragmentHomeBinding
import com.inertia.data.entity.WeatherEntity
import com.inertia.utils.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = WeatherEntity(24.08, 14, 0.58, 93)
        setWeather(weather)
        setDropdownItem()
        getBencanaData()
    }

    private fun getBencanaData() {
        val adapter = BencanaAdapter()
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvBencana.adapter = adapter
        binding.rvBencana.layoutManager = layoutManager
        viewModel.getAllBencana().observe(this, {
            adapter.setData(it)
        })

    }

    private fun setDropdownItem() {
        val sortFilter = arrayOf("Baru baru ini", "Terjadi didekatmu", "Apalagi ya")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, sortFilter)
        binding.spinner.adapter = arrayAdapter
    }

    private fun setWeather(weather: WeatherEntity) {
        with(binding) {
            layoutWeather.tvTemp.text = getString(R.string.temp, weather.temp)
            layoutWeather.tvCloud.text = getString(R.string.cloud, weather.cloud)
            layoutWeather.tvWind.text = getString(R.string.wind, weather.wind)
            layoutWeather.tvHumidity.text = getString(R.string.humidity, weather.humidity)
        }
    }
}