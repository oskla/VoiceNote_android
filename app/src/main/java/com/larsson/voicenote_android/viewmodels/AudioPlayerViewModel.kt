package com.larsson.voicenote_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.audioplayer.AudioPlayer
import com.larsson.voicenote_android.audioplayer.PlaybackState
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ExpandedContainerState(
    val isExpanded: Boolean,
    val recordingId: String,
)

class AudioPlayerViewModel(private val audioPlayer: AudioPlayer) : ViewModel() {

    private val TAG = "AudioPlayerViewModel"

    //    val playerState: StateFlow<PlayerState> = audioPlayer.playerState
    val isPlaying = audioPlayer.currentPlaybackState
        .map { it == PlaybackState.Playing }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val currentPosition: StateFlow<Long> = audioPlayer.currentPosition

    private val _isExpanded = MutableStateFlow<ExpandedContainerState>(ExpandedContainerState(isExpanded = false, recordingId = ""))
    val isExpanded: StateFlow<ExpandedContainerState> = _isExpanded

    override fun onCleared() {
        super.onCleared()
        cleanup()
    }

    // TODO add here a clear or stop function that is triggered when leaving screen (back button)
    fun handleUIEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> pause()
            is AudioPlayerEvent.Play -> play()
            AudioPlayerEvent.SetToIdle -> {}
            is AudioPlayerEvent.SeekTo -> seekTo(event.position)
            is AudioPlayerEvent.ToggleExpanded -> {
                _isExpanded.value = ExpandedContainerState(isExpanded = event.shouldExpand, recordingId = event.recordingId)

                if (event.shouldExpand) {
                    prepare(event.recordingId)
                } else {
                    pause() // TODO not sure if pause is the right thing to do here. I seem to save the state so why not? I dont think stopping is correct here.
                }
            }
        }
    }

    fun prepare(recordingId: String) {
        audioPlayer.prepare(recordingId)
    }

    private fun play() {
        audioPlayer.play()
    }

    fun pause() {
        audioPlayer.pause()
    }

    private fun seekTo(position: Int) { // FIXME change to Long
        viewModelScope.launch {
            audioPlayer.seekTo(position.toLong())
        }
    }

    private fun cleanup() {
        viewModelScope.launch {
            audioPlayer.stop()
        }
    }
}
