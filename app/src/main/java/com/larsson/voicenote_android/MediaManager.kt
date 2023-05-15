package com.larsson.voicenote_android

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import java.io.File
import java.io.IOException

class MediaManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var onPreparedListener: (() -> Unit)? = null
    private var currentPath: String? = null // TODO remove this?
    private var currentRecordingId: String? = null

    fun start(recordingId: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        try {
            val file = File(context.cacheDir, recordingId).absolutePath.apply { currentPath = this }
            Log.d("audioMediaManager", "currentPath: $currentPath")
            Log.d("audioMediaManager", "recordingID: $recordingId")
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(file)
            mediaPlayer?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.start()
    }

    fun resume() {
        mediaPlayer?.start()
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun reset() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun getDuration(): Int? {
        return mediaPlayer?.duration
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun getCurrentPosition(): Int? {
        return mediaPlayer?.currentPosition
    }

    fun isPlaying(): Boolean? {
        return mediaPlayer?.isPlaying
    }
}
