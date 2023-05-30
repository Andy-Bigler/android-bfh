package com.example.streaming

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class StreamService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startStreaming()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStreaming()
    }

    private fun createNotification(): Notification {
        val channelId = "StreamServiceChannel"
        val channelName = "StreamService"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setContentTitle("Streaming")
            .setContentText("Streaming in progress").priority = NotificationManager.IMPORTANCE_HIGH

        return notificationBuilder.build()
    }

    private fun startStreaming() {
        val url = getString(R.string.stream)

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
        }

        mediaPlayer?.setOnPreparedListener {
            Toast.makeText(this, getString(R.string.streaming_started), Toast.LENGTH_SHORT).show()
            startForeground(1, createNotification())
            it.start()
        }

        mediaPlayer?.setOnCompletionListener {
            Toast.makeText(this, getString(R.string.streaming_finished), Toast.LENGTH_SHORT).show()
            stopSelf()
        }

        mediaPlayer?.setOnErrorListener { _, _, _ ->
            Toast.makeText(this, getString(R.string.error_occurred_while_streaming), Toast.LENGTH_SHORT).show()
            stopSelf()
            true
        }
    }

    private fun stopStreaming() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        Toast.makeText(this, getString(R.string.streaming_stopped), Toast.LENGTH_SHORT).show()
    }

}
