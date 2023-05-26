package com.larsson.voicenote_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.MediaManager
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioPlayerViewModel(private val mediaManager: MediaManager) : ViewModel() {

    private val TAG = "AudioPlayerViewModel"

    val playerState: StateFlow<PlayerState> = mediaManager.playerState
    val currentPosition: StateFlow<Int> = mediaManager.currentPosition

    init {
        viewModelScope.launch {
            mediaManager.setOnCompletionListener(::handleCompletion)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cleanup()
    }

    // TODO add here a clear or stop function that is triggered when leaving screen (back button)
    fun handleUIEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> { pause() }
            is AudioPlayerEvent.Play -> { play(event.recordingId) }
            AudioPlayerEvent.SetToIdle -> { reset() }
            is AudioPlayerEvent.SeekTo -> seekTo(event.position)
        }
    }

    private fun setPlayerState(state: PlayerState) {
        viewModelScope.launch {
            mediaManager.setPlayerState(state)
        }
    }

    private fun handleCompletion() {
        setPlayerState(PlayerState.Completed)
        mediaManager.getDuration()?.let { position ->
            seekTo(position)
        }
    }

    private fun play(recordingId: String) {
        // Resume track if one is already paused
        if (playerState.value is PlayerState.Paused) {
            viewModelScope.launch {
                mediaManager.resume()
            }
            return
        }

        // Playing new track
        viewModelScope.launch {
            mediaManager.start(recordingId)
        }
    }

    fun pause() {
        viewModelScope.launch {
            mediaManager.pause()
        }
    }

    private fun reset() {
        viewModelScope.launch {
            mediaManager.reset()
        }
    }

    private fun seekTo(position: Int) {
        viewModelScope.launch {
            mediaManager.seekTo(position)
        }
    }

    private fun cleanup() {
        viewModelScope.launch {
            mediaManager.stop()
        }
    }
}
