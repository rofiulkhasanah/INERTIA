package com.inertia.utils

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.inertia.ui.home.HomeFragment
import java.lang.ClassCastException

object LocationProvider {
    fun getLocation(activity: Activity): LiveData<Location> {

        val locationLiveData = MutableLiveData<Location>()

        //request permission
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), 101)
        }

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        //location request builder
        val interval: Long = 100 * 60 * 5
        val fastestInterval: Long = 100 * 30
        val request = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(interval)
            .setFastestInterval(fastestInterval)

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(request)
            .setAlwaysShow(false)

        val checkLocationSettings = LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())

        checkLocationSettings.addOnCompleteListener {
            fusedLocationProviderClient.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {

                    for (item in p0.locations) {
                        locationLiveData.postValue(item)
                    }
                }
            }, Looper.getMainLooper())
        }
        checkLocationSettings.addOnFailureListener { exception ->
            if(exception is ResolvableApiException) {
                when(exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            //minta lokasi biar dinyalain
                            exception.startResolutionForResult(
                                activity,
                                HomeFragment.REQUEST_CHECK_SETTINGS
                            )
                        }catch (e: IntentSender.SendIntentException) {

                        }catch (e: ClassCastException) {

                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Toast.makeText(activity, "Lokasi tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return locationLiveData
    }
}