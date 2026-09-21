package space.attaditya.littleflight.webview

import android.app.Activity
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import space.attaditya.littleflight.BuildConfig
import space.attaditya.littleflight.device.DeviceLocation

class WvBridge(
  private val context: Context,
  private val webView: WebView,
  private val location: DeviceLocation,
) {
  @JavascriptInterface
  fun required(): Boolean {
    return true
  }

  @JavascriptInterface
  fun appVersion(): Int {
    return BuildConfig.VERSION_CODE
  }
}

