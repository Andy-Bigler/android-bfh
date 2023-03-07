package com.example.sms

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
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
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSms()
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this, "Permission required to send a SMS", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(arrayOf(android.Manifest.permission.SEND_SMS), PERMISSIONS_REQUEST_SEND_SMS)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms()
                } else {
                    Toast.makeText(this, "Permission denied. Not able to send SMS", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendSms() {
        try {
            val smsManager = applicationContext.getSystemService(SmsManager::class.java)
            smsManager.sendTextMessage(phoneNumber.text.toString(), null, message.text.toString(), null, null)
            message.setText("")
            Toast.makeText(this, "Successfully sent SMS", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show()
        }
    }
}
