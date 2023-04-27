package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BatteryStatusReceiver : BroadcastReceiver() {

    companion object {
        const val LOCAL_BROADCAST_ACTION = "battery_status_update"
        const val EXTRA_IS_CONNECTED = "is_connected"
        const val EXTRA_BATTERY_PERCENTAGE = "battery_percentage"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        val isConnected = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS

        val localIntent = Intent(LOCAL_BROADCAST_ACTION).apply {
            putExtra(EXTRA_IS_CONNECTED, isConnected)
            putExtra(EXTRA_BATTERY_PERCENTAGE, batteryPercentage)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
    }
}

