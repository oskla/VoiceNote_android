package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.MediaManager
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioPlayerViewModel(private val mediaManager: MediaManager) : ViewModel() {

    private val TAG = "AudioPlayerViewModel"

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Idle)
    val playerState: StateFlow<PlayerState>
        get() = _playerState

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int>
        get() = _currentPosition

    init {

        if (isPlaying() == true) {
            viewModelScope.launch {
                _playerState.emit(PlayerState.Playing)
            }
        }

        mediaManager.setOnCompletionListener(::handleCompletion)
    }

    fun handleUIEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> { pause() }
            is AudioPlayerEvent.Play -> { play(event.recordingId) }
            AudioPlayerEvent.SetToIdle -> { reset() }
        }
    }

    private fun handleCompletion() {
        viewModelScope.launch {
            _playerState.emit(PlayerState.Completed)
        }
    }

    private fun resume() {
        Log.d(TAG, "Resume pressed")
        mediaManager.resume()
        updateCurrentPosition()
        viewModelScope.launch {
            _playerState.emit(PlayerState.Playing)
        }
        return
    }

    fun play(recordingId: String) {
        if (playerState.value is PlayerState.Paused) {
            resume()
            return
        }

        // Playing new track
        mediaManager.start(recordingId)
        viewModelScope.launch {
            if (isPlaying() == true) {
                updateCurrentPosition() // while loop
                _playerState.emit(PlayerState.Playing)
            } else {
                _playerState.emit(
                    PlayerState.Error(Throwable("Error playing")),
                )
            }
        }
    }
    private fun updateCurrentPosition() {
        viewModelScope.launch {
            while (isPlaying() == true) {
                delay(200)
                Log.d(TAG, "currentPosition: ${mediaManager.getCurrentPosition()}")
                _currentPosition.emit(mediaManager.getCurrentPosition() ?: 0)
            }
        }
    }

    fun pause() {
        mediaManager.pause()
        _playerState.value = PlayerState.Paused
    }

    private fun getDuration(): Int? {
        return mediaManager.getDuration()
    }

    private fun reset() {
        mediaManager.reset()
        _currentPosition.value = 0
        _playerState.value = PlayerState.Idle
    }

    fun seekTo(position: Int) {
        mediaManager.seekTo(position)

        viewModelScope.launch {
            _currentPosition.emit(position)
            _playerState.emit(PlayerState.Paused)
        }
    }

    private fun isPlaying(): Boolean? {
        return mediaManager.isPlaying()
    }

    sealed class PlayerState {
        object Idle : PlayerState()
        object Playing : PlayerState()
        object Paused : PlayerState()
        object Completed : PlayerState()
        data class Error(val throwable: Throwable?) : PlayerState()
    }
}
