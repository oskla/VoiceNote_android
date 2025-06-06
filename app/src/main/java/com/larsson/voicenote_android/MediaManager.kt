package com.larsson.voicenote_android

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import java.io.File
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MediaManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    private var onCompletionListener: (() -> Unit)? = null
    private var currentPath: String? = null // TODO remove this?

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Idle)
    val playerState: StateFlow<PlayerState> = _playerState

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition

    suspend fun start(recordingId: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }

        try {
            val file = File(context.cacheDir, recordingId).absolutePath.apply { currentPath = this }
            mediaPlayer?.reset()

            mediaPlayer?.setDataSource(file)
            _playerState.emit(PlayerState.Initialized)

            mediaPlayer?.prepare()
            _playerState.emit(PlayerState.Prepared)
        } catch (e: Exception) {
            _playerState.emit(PlayerState.Error(e))
            e.printStackTrace()
        }

        mediaPlayer?.setOnCompletionListener {
            onCompletionListener?.invoke()
        }

        mediaPlayer?.start()
        _playerState.emit(PlayerState.Playing)
        setCurrentPosition()
    }

    suspend fun resume() {
        mediaPlayer?.start()
        _playerState.emit(PlayerState.Playing)
        setCurrentPosition()
    }

    suspend fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
        setCurrentPosition(position)
    }

    fun setOnCompletionListener(listener: (() -> Unit)) {
        onCompletionListener = listener
        Log.d("mediaPlayer", "completeListener is set")
    }

    suspend fun stop() {
        mediaPlayer?.stop()
        _playerState.emit(PlayerState.Stopped)

        mediaPlayer?.release()
        _playerState.emit(PlayerState.End)

        mediaPlayer = null
    }

    suspend fun reset() {
        mediaPlayer?.reset()
        setPlayerState(PlayerState.Idle)
        setCurrentPosition(0)
    }

    private suspend fun setCurrentPosition(position: Int? = null) {
        if (position != null) {
            _currentPosition.emit(position)
            return
        }

        while (mediaPlayer?.isPlaying == true) {
            if (mediaPlayer?.currentPosition != null) {
                _currentPosition.emit(mediaPlayer?.currentPosition!!)
            }

            delay(200)
        }
    }

    fun getDuration(): Int? {
        return mediaPlayer?.duration
    }

    suspend fun pause() {
        mediaPlayer?.pause()
        setPlayerState(PlayerState.Paused)
    }

    suspend fun setPlayerState(state: PlayerState) {
        _playerState.emit(state)
    }
}

sealed class PlayerState {
    object Idle : PlayerState()
    object End : PlayerState()
    object Initialized : PlayerState()
    object Playing : PlayerState()
    object Prepared : PlayerState()
    object Stopped : PlayerState()
    object Paused : PlayerState()
    object Completed : PlayerState()
    data class Error(val throwable: Throwable?) : PlayerState()
}
