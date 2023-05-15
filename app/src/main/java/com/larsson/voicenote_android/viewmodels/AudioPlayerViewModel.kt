package com.larsson.voicenote_android.viewmodels

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

        if (mediaManager.isPlaying() == true) {
            _playerState.value = PlayerState.Playing()
        }

        viewModelScope.launch {
            while (true) {
                if (mediaManager.isPlaying() == true) {
                    _currentPosition.value = mediaManager.getCurrentPosition() ?: 0
                }
                delay(100)
            }
        }
    }

    fun handlePlayerEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> {
                pause()
            }
            is AudioPlayerEvent.Play -> {
                play(event.recordingId)
            }
            AudioPlayerEvent.SetToComplete -> TODO()
            AudioPlayerEvent.SetToIdle -> {
                reset()
            }
        }
    }

    private fun resume() {
        mediaManager.resume()
        _playerState.value = PlayerState.Playing()
        return
    }

    fun play(recordingId: String) {
        if (playerState.value is PlayerState.Paused) {
            resume()
            viewModelScope.launch {
                _playerState.emit(
                    PlayerState.Playing(
                        duration = getDuration(),
                    ),
                )
            }
            return
        }

        // Playing new track
        mediaManager.start(recordingId)
        viewModelScope.launch {
            if (isPlaying() == true) {
                _playerState.emit(
                    PlayerState.Playing(
                        duration = getDuration() ?: 0,
                    ),
                )
            } else {
                _playerState.emit(
                    PlayerState.Error(Throwable("Error playing")),
                )
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
        }
    }

    private fun isPlaying(): Boolean? {
        viewModelScope.launch {
            _playerState.emit(
                PlayerState.Playing(
                    duration = getDuration() ?: 0,
                ),
            )
        }
        return mediaManager.isPlaying()
    }

    sealed class PlayerState {
        object Idle : PlayerState()
        data class Playing(val duration: Int? = null) : PlayerState() // TODO not sure if needed. Now this information is gathered from room
        object Paused : PlayerState()
        object Completed : PlayerState()
        data class Error(val throwable: Throwable?) : PlayerState()
    }
}
