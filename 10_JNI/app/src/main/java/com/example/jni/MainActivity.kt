package com.example.jni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    init {
        System.loadLibrary("native-lib")
    }

    private external fun encodeBase64(input: String): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            val input = editText.text.toString()
            if (input.isBlank()) {
                Toast.makeText(this, "Please enter a text", Toast.LENGTH_SHORT).show()
                textView.text = ""
                return@setOnClickListener
            }

            val encoded = encodeBase64(input)

            textView.text = encoded
        }
    }
}