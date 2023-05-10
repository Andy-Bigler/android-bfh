package com.example.jni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Load the native library
    init {
        System.loadLibrary("native-lib")
    }

    external fun encodeBase64(input: String): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val originalString = "Hello, World!"
        val encodedString = encodeBase64(originalString)

        Toast.makeText(this, "Base64: $encodedString", Toast.LENGTH_LONG).show()
    }
}