package vv.eighteight.w88conthoismash

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vv.eighteight.w88conthoismash.databinding.FragmentThirdBinding
import vv.eighteight.w88conthoismash.services.NetworkConnection
import vv.eighteight.w88conthoismash.view.app.AppViewModel

class FragmentWThird : Fragment() {

    private var fragmentInfoBinding : FragmentThirdBinding? = null
    private val _infoBinding get() = fragmentInfoBinding!!

    private lateinit var viewModel : AppViewModel

    private var connCheck = NetworkConnection()
    private var checkNet = false

    var title: String? = null
    var imgUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentInfoBinding = FragmentThirdBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        checkNetConnection()
        title = arguments?.getString("title")
        return _infoBinding.root
    }

    private fun checkNetConnection() {
        checkNet = connCheck.connectionError(requireActivity())
        if (checkNet) {
            viewModel.initJson()
            getJumpCode()
        } else {
            Toast.makeText(context, "Connection error, Please check internet connection.",
                Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getJumpCode() {
        viewModel.dataListModel.observe(viewLifecycleOwner) {
            it.let {
                if (it != null) {
                    for (i in it.indices) {
                        val name = it[i].APPW88PACKAGE
                        val url = it[i].APPW88URL
                        val status = it[i].APPW88URLSTATUS
                        val webView: WebView = _infoBinding.infoWebView
                        if (name == context?.packageName) {
                            Log.e(ContentValues.TAG, name.toString())
                            when (status) {
                                true -> {
                                    webView.loadUrl(url.toString())
                                }
                                else -> {
                                    if (title == "webView") {
                                        webView.loadUrl("file:///android_asset/appWeb/infoWeb.html")
                                    } else {
                                        webView.loadUrl("file:///android_asset/appWeb/infoWeb.html")
                                    }
                                }
                            }
                            webViewInit()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewInit() {
        with(_infoBinding.infoWebView) {
            with(settings) {
                javaScriptEnabled = true
                defaultTextEncodingName = "UTF-8"
                cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
                useWideViewPort = true
                pluginState = android.webkit.WebSettings.PluginState.ON
                domStorageEnabled = true
                builtInZoomControls = false
                layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                loadWithOverviewMode = true
                blockNetworkImage = true
                loadsImagesAutomatically = true
                setSupportZoom(false)
                setSupportMultipleWindows(true)

            }
            requestFocusFromTouch()
            scrollBarStyle = android.view.View.SCROLLBARS_INSIDE_OVERLAY
        }

        val webSetting: WebSettings = _infoBinding.infoWebView.settings
        with(webSetting) {
            context?.getDir(
                "cache", AppCompatActivity.MODE_PRIVATE
            )?.path
            domStorageEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        _infoBinding.infoWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                _infoBinding.infoProgressBar.progress = newProgress
                if (newProgress == 100) {
                    _infoBinding.infoWebView.settings.blockNetworkImage = false
                }
            }

            override fun onCreateWindow(
                view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message,
            ): Boolean {
                val newWebView = context?.let { WebView(it) }
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                newWebView?.webViewClient = object : WebViewClient() {
                    @Deprecated("Deprecated in Java")
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        _infoBinding.infoWebView.loadUrl(url)
                        if (url.startsWith("http") || url.startsWith("https")) {
                            return super.shouldOverrideUrlLoading(view, url)
                        } else if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith(WebView.SCHEME_MAILTO)) {
                            val dialIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(dialIntent)
                        } else {
                            try {
                                context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

                            } catch (ex: ActivityNotFoundException) {
                                val makeShortText = "The Application has not been installed"
                                Toast.makeText(context, makeShortText, Toast.LENGTH_SHORT).show()
                            }
                        }
                        return true
                    }
                }
                return true
            }
        }

        val settings: WebSettings = _infoBinding.infoWebView.settings
        settings.javaScriptEnabled = true
        _infoBinding.infoWebView.setOnLongClickListener { v: View ->
            val result = (v as WebView).hitTestResult
            val type = result.type
            if (type == WebView.HitTestResult.UNKNOWN_TYPE) return@setOnLongClickListener false
            when (type) {
                WebView.HitTestResult.PHONE_TYPE -> {}
                WebView.HitTestResult.EMAIL_TYPE -> {}
                WebView.HitTestResult.GEO_TYPE -> {}
                WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {}
                WebView.HitTestResult.IMAGE_TYPE -> {
                    imgUrl = result.extra
                }
                else -> {}
            }
            true
        }

        _infoBinding.infoWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                _infoBinding.infoProgressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                _infoBinding.infoProgressBar.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView, request: WebResourceRequest, error: WebResourceError,
            ) {
                super.onReceivedError(view, request, error)
            }


        }

        _infoBinding.infoWebView.setOnKeyListener { _: View?, i: Int, keyEvent: KeyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK && _infoBinding.infoWebView.canGoBack()) {
                    _infoBinding.infoWebView.goBack()
                    return@setOnKeyListener true
                }

            }
            false
        }

    }
}