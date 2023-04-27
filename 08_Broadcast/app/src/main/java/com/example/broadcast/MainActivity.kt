package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    private lateinit var batteryStatusTextView: TextView
    private lateinit var powerConnectionTextView: TextView
    private lateinit var batteryStatusReceiver: BatteryStatusReceiver
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryStatusTextView = findViewById(R.id.batteryStatusTextView)
        powerConnectionTextView = findViewById(R.id.powerConnectionTextView)
        batteryStatusReceiver = BatteryStatusReceiver()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
    }

    private val localReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val isConnected = intent.getBooleanExtra(BatteryStatusReceiver.EXTRA_IS_CONNECTED, false)
            val batteryPercentage = intent.getIntExtra(BatteryStatusReceiver.EXTRA_BATTERY_PERCENTAGE, -1)

            batteryStatusTextView.text = "Battery Status: $batteryPercentage%"
            powerConnectionTextView.text = "Power Connection: \n${if (isConnected) "Connected" else "Disconnected"}"
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(batteryStatusReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_BATTERY_CHANGED)
        })

        localBroadcastManager.registerReceiver(localReceiver, IntentFilter(BatteryStatusReceiver.LOCAL_BROADCAST_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryStatusReceiver)
        localBroadcastManager.unregisterReceiver(localReceiver)
    }
}

