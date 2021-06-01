package com.inertia.utils

import android.Manifest
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.inertia.ui.home.HomeFragment
import java.lang.ClassCastException

object LocationProvider {
    fun getLocation(activity: Activity): Location? {

        var location: Location? = null

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
            return null
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
            try {
                //request location updates
                fusedLocationProviderClient.requestLocationUpdates(request, object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {

                        for (item in p0.locations) {
                            location = item
                        }
                    }
                }, Looper.getMainLooper())
            }catch (e: ApiException) {
                when(e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvable = e as ResolvableApiException

                            //minta lokasi biar dinyalain
                            resolvable.startResolutionForResult(
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
        return location
    }
}