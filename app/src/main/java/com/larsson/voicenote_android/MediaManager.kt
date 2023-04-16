package com.larsson.voicenote_android

import android.content.Context
import android.media.MediaPlayer
import java.io.File
import java.io.IOException

class MediaManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var onPreparedListener: (() -> Unit)? = null

    fun start(path: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(File(context.cacheDir, path).absolutePath)
            mediaPlayer?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
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
