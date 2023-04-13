package com.example.ip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                val ipInfo = fetchIPInfo()
                textView.text = ipInfo?.toString() ?: "Failed to fetch IP information"
            }
        }
    }

    private suspend fun fetchIPInfo(): IPInfo? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://ipinfo.io/json")
            .build()

        return@withContext try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()?.let { jsonString ->
                    val adapter = moshi.adapter(IPInfo::class.java)
                    adapter.fromJson(jsonString)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error" + e.message)
            null
        }
    }
}