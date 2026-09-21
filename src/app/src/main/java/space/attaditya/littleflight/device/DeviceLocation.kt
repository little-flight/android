package space.attaditya.littleflight.device

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

class DeviceLocation(
  context: Context
) {
  private val fusedClient = LocationServices
    .getFusedLocationProviderClient(context)

  private val settingsClient = LocationServices
    .getSettingsClient(context)

  fun getLocation(
    activity: Activity,
    callback: (latitude: Float, longitude: Float, accuracy: Float) -> Boolean,
    onLocationDisabled: () -> Boolean
  ) {
    val request = LocationRequest.Builder(
      Priority.PRIORITY_HIGH_ACCURACY,
      1000
    ).build()

    val settingsRequest = LocationSettingsRequest.Builder()
      .addLocationRequest(request)
      .build()

    settingsClient.checkLocationSettings(settingsRequest)
      .addOnSuccessListener {
        fusedClient.getCurrentLocation(
          Priority.PRIORITY_HIGH_ACCURACY,
          null
        ).addOnSuccessListener { location ->
          location?.let {
            callback(
              it.latitude.toFloat(),
              it.longitude.toFloat(),
              it.accuracy
            )
          }
        }
      }

      .addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
          val exc: ResolvableApiException = exception
          exc.startResolutionForResult(
            activity,
            1001
          )
        }

        onLocationDisabled()
      }
  }
}

