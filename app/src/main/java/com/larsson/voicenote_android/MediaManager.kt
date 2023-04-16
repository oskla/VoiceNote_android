package com.larsson.voicenote_android

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException

class MediaManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var onPreparedListener: (() -> Unit)? = null

    fun start(path: String = "/data/data/com.larsson.voicenote_android/cache/SoundHelix-Song-1.mp3") {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            try {
                mediaPlayer!!.setDataSource(path)
                mediaPlayer!!.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun pause() {
        mediaPlayer?.pause()
    }
}
