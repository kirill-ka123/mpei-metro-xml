package ru.mpei.metro.presentation.map.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.map.MapViewModel
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

private const val DEFAULT_LOCATION_MIN_TIME_MS = 5000L
private const val DEFAULT_LOCATION_MIN_DISTANCE = 10F

@MapFragmentScope
class LocationController @Inject constructor(
    private val activity: AppCompatActivity,
    private val mapViewModel: MapViewModel,
    private val metroGraphProvider: MetroGraphProvider,
) : FragmentOnCreateViewListener {
    private val locationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var lastLocationRequest: LocationRequest? = null

    private val locationPermissionsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        if (fineLocationGranted && coarseLocationGranted) {
            lastLocationRequest?.let { startLocationUpdatesWithGrantedPermission(it) }
        }
    }

    override fun onCreateView(lifecycleOwner: LifecycleOwner) {
        startLocationUpdatesWithPermissionsRequest(
            listener = { location ->
                mapViewModel.onLocationChanged(metroGraphProvider.getMetroGraph(), location)
            }
        )
    }

    private fun startLocationUpdatesWithPermissionsRequest(
        listener: LocationListener,
        minTimeMs: Long = DEFAULT_LOCATION_MIN_TIME_MS,
        minDistance: Float = DEFAULT_LOCATION_MIN_DISTANCE,
    ) {
        if (hasLocationPermissions(activity)) {
            locationPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        } else {
            startLocationUpdatesWithGrantedPermission(listener, minTimeMs, minDistance)
        }
    }

    private fun startLocationUpdatesWithGrantedPermission(
        locationRequest: LocationRequest,
    ) = startLocationUpdatesWithGrantedPermission(
        listener = locationRequest.listener,
        minTimeMs = locationRequest.minTimeMs,
        minDistance = locationRequest.minDistance,
    )

    @SuppressLint("MissingPermission")
    private fun startLocationUpdatesWithGrantedPermission(
        listener: LocationListener,
        minTimeMs: Long = DEFAULT_LOCATION_MIN_TIME_MS,
        minDistance: Float = DEFAULT_LOCATION_MIN_DISTANCE,
    ) {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            minTimeMs,
            minDistance,
            listener,
        )
    }

    private fun hasLocationPermissions(context: Context) =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED

    private data class LocationRequest(
        val listener: LocationListener,
        val minTimeMs: Long = DEFAULT_LOCATION_MIN_TIME_MS,
        val minDistance: Float = DEFAULT_LOCATION_MIN_DISTANCE,
    )
}
