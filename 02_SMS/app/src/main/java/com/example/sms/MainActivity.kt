package com.example.sms

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var phoneNumber: EditText
    private lateinit var message: EditText
    private lateinit var sendButton: Button

    companion object Const {
        const val PERMISSIONS_REQUEST_SEND_SMS = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneNumber = findViewById(R.id.phone_number)
        message = findViewById(R.id.message)
        sendButton = findViewById(R.id.button)

        sendButton.setOnClickListener {
            Toast.makeText(this, "Sending message...", Toast.LENGTH_SHORT).show()
        }
    }
}

// ToDo
// checkSelfPermission
// requestPermissions
// shouldShowRequestPermissionRationale
// onRequestPermissionsResult