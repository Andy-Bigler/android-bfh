package com.example.multilanguage

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var playing = false;
        val player = MediaPlayer.create(this, R.raw.melody)

        playButton = findViewById(R.id.playButton)
        playButton.setOnClickListener {
            if (playing) {
                player.pause()
                playButton.text = getString(R.string.play)
            } else {
                player.start()
                playButton.text = getString(R.string.pause)
            }
            playing = !playing
        }
    }
}