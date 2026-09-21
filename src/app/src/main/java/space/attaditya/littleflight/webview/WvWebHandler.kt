package space.attaditya.littleflight.webview

import android.content.Context
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import space.attaditya.littleflight.device.DeviceLocation
import space.attaditya.littleflight.utils.Config

class WvWebHandler(
  private val context: Context,
) {
  private var webView: WebView? = null
  fun create(): WebView {
    return WebView(context).apply {
      settings.javaScriptEnabled = true
      settings.domStorageEnabled = true
      settings.mediaPlaybackRequiresUserGesture = false
      settings.useWideViewPort = false
      settings.loadWithOverviewMode = false
      settings.allowFileAccess = true
      settings.allowContentAccess = true
      settings.loadsImagesAutomatically = true
      settings.javaScriptCanOpenWindowsAutomatically = true
      settings.setSupportMultipleWindows(true)
      WebView.setWebContentsDebuggingEnabled(true)
      webViewClient = WebViewClient()
      webView = this
      isHapticFeedbackEnabled = false
      setInitialScale(200)
      CookieManager.getInstance().apply {
        setAcceptCookie(true)
        setAcceptThirdPartyCookies(webView, true)
      }

      val bridge = WvBridge(
        context,
        this,
        DeviceLocation(context)
      )

      addJavascriptInterface(bridge, Config.BRIDGE_NAME)
      loadUrl(Config.WEBVIEW_URL)
    }
  }

  fun canGoBack(): Boolean {
    return webView?.canGoBack() == true
  }

  fun goBack() {
    webView?.goBack()
  }
}

