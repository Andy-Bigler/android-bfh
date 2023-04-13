package com.example.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled") // We know what we're doing ;)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.addJavascriptInterface(JavaScriptInterface(this), "AndroidInterface")
        webView.loadDataWithBaseURL(null, getHtmlContent(), "text/html", "UTF-8", null)
    }

    private fun getHtmlContent(): String {
        return "<html><body><button type=\"button\" onclick=\"AndroidInterface.showToast('Hello from WebView!')\">Click me!</button></body></html>"
    }
}