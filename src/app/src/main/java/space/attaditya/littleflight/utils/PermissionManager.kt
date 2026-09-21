package space.attaditya.littleflight.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionManager {
  companion object {
    private const val LOCATION_REQUEST = 1001
    fun requestLocation(activity: Activity) {
      if (
        activity.checkSelfPermission(
          Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        ActivityCompat.requestPermissions(
          activity,
          arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
          ),
          LOCATION_REQUEST
        )
      }
    }
  }
}

