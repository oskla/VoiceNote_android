package com.larsson.voicenote_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.MediaManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioPlayerViewModel(private val mediaManager: MediaManager) : ViewModel() {

    data class AudioItem(
        val recordingId: String,
        var isPlaying: Boolean = false,
    )

    private val TAG = "AudioPlayerViewModel"

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Idle)
    val playerState: StateFlow<PlayerState>
        get() = _playerState

    private val _audioItems = MutableStateFlow<List<AudioItem>>(emptyList())
    val audioItems: StateFlow<List<AudioItem>>
        get() = _audioItems

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int>
        get() = _currentPosition

    init {

        if (mediaManager.isPlaying() == true) {
            _playerState.value = PlayerState.Playing
        }

        viewModelScope.launch {
            while (true) {
                if (mediaManager.isPlaying() == true) {
                    _currentPosition.value = mediaManager.getCurrentPosition() ?: 0
                }
                delay(1000)
            }
        }
    }

    private fun pauseAllAudioItems() {
        _audioItems.update { audioItems ->
            audioItems.map { audioItem ->
                audioItem.copy(isPlaying = false)
            }
        }
    }

    private fun updateToIsPlaying(recordingId: String) {
        _audioItems.update { audioItems ->
            audioItems.map { audioItem ->
                audioItem.copy(isPlaying = true)
            }
        }
    }
    private fun resume(recordingId: String) {
        val updatePlayingState = audioItems.value.map { audioItem ->
            if (audioItem.recordingId == recordingId) {
                mediaManager.resume()
                audioItem.copy(isPlaying = true)
            } else {
                audioItem.copy(isPlaying = false)
            }
        }

        _audioItems.value = updatePlayingState
        _playerState.value = PlayerState.Playing
        return
    }

    fun play(recordingId: String) {
        // TODO having problem with setting the play button back to PLAY
        if (isThisItemPlaying()) {
            resume(recordingId)
            updateToIsPlaying(recordingId)
            return
        }

        // Playing new track
        val newAudioItem = AudioItem(recordingId, isPlaying = true)
        _audioItems.value = _audioItems.value + newAudioItem
        mediaManager.start(newAudioItem.recordingId)

        if (isPlaying() == true) {
            _playerState.value = PlayerState.Playing
        } else {
            _playerState.value = PlayerState.Error(Throwable("Error playing"))
        }
    }

    fun pause() {
        mediaManager.pause()

        // Set all audioItems to isPlaying = false
        pauseAllAudioItems()

        _playerState.value = PlayerState.Paused
    }

    private fun getCurrentRecordingId(): String? {
        return mediaManager.getCurrentRecordingId()
    }
    private fun isPlaying(): Boolean? {
        _playerState.value = PlayerState.Playing
        return mediaManager.isPlaying()
    }

    private fun isThisItemPlaying(): Boolean {
        val currentRecordId = mediaManager.getCurrentRecordingId()

        return currentRecordId != null
    }

    sealed class PlayerState {
        object Idle : PlayerState()
        object Playing : PlayerState()
        object Paused : PlayerState()
        object Completed : PlayerState()
        data class Error(val throwable: Throwable?) : PlayerState()
    }
}
