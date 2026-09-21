package space.attaditya.littleflight

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import space.attaditya.littleflight.webview.WvWebHandler

class MainActivity : ComponentActivity() {
  private lateinit var webHandler: WvWebHandler
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    webHandler = WvWebHandler(this)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.addFlags(
      WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
    )

    val controller = WindowCompat.getInsetsController(
      window,
      window.decorView
    )

    controller.hide(WindowInsetsCompat.Type.systemBars())
    controller.systemBarsBehavior =
      WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    onBackPressedDispatcher.addCallback(
      this,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          if (webHandler.canGoBack()) {
            webHandler.goBack()
          } else {
            isEnabled = false
            onBackPressedDispatcher.onBackPressed()
          }
        }
      }
    )

    setContent {
      AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
          webHandler.create()
        }
      )
    }
  }
}

