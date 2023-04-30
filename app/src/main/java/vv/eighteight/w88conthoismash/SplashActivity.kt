package vv.eighteight.w88conthoismash

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import vv.eighteight.w88conthoismash.databinding.ActivitySplashBinding
import vv.eighteight.w88conthoismash.services.NetworkConnection
import vv.eighteight.w88conthoismash.view.app.AppViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var _splashBinding : ActivitySplashBinding
    private lateinit var viewModel : AppViewModel

    private lateinit var webViewDialog : Dialog

/*    private var connCheck = NetworkConnection()
    private var checkNet = false

    var imgUrl : String? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_splashBinding.root)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        showWebViewDialog()

    }

    private fun showWebViewDialog() {
        webViewDialog = Dialog(this)
        /*webViewDialog.setTitle("Chào mừng!")*/
        _splashBinding.splashWebView.loadUrl("file:///android_asset/appWeb/splashNotice.html")

        _splashBinding.close.setOnClickListener {

            webViewDialog.dismiss()
            _splashBinding.splashWebView.visibility = View.GONE
            _splashBinding.close.visibility = View.GONE
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },2000)
        }
    }
}


