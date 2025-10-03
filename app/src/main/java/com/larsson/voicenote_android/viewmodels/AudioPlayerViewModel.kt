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

data class ExpandedContainerState(
    val isExpanded: Boolean,
    val recordingId: String,
)

class AudioPlayerViewModel(
    private val audioPlayer: AudioPlayer,
) : ViewModel() {
    private val TAG = "AudioPlayerViewModel"

    val isPlaying = audioPlayer.currentPlaybackState
        .map { it == PlaybackState.Playing }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val currentPosition: StateFlow<Long> = audioPlayer.currentPosition

    private val _isExpanded = MutableStateFlow<ExpandedContainerState>(ExpandedContainerState(isExpanded = false, recordingId = ""))
    val isExpanded: StateFlow<ExpandedContainerState> = _isExpanded

    // TODO add here a clear or stop function that is triggered when leaving screen (back button)
    fun handleUIEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> pause()
            is AudioPlayerEvent.Play -> play(recordingId = event.recordingId)
            AudioPlayerEvent.SetToIdle -> {}
            is AudioPlayerEvent.SeekTo -> seekTo(position = event.position)
            AudioPlayerEvent.OnSeekFinished -> onSeekFinished()
            is AudioPlayerEvent.ToggleExpanded -> toggleExpanded(event)
        }
    }

    private fun toggleExpanded(event: AudioPlayerEvent.ToggleExpanded) {
        _isExpanded.value = ExpandedContainerState(isExpanded = event.shouldExpand, recordingId = event.recordingId)

        if (!event.shouldExpand) {
            pause()
        }
    }


    private fun play(recordingId: String) {
        audioPlayer.prepare(recordingId) // TODO maybe check if this is not already playing
        audioPlayer.play()
    }

    private fun pause() {
        audioPlayer.pause()
    }

    private fun seekTo(position: Int) { // FIXME change to Long
        audioPlayer.seekTo(position.toLong())
    }

    private fun stop() {
        audioPlayer.stop()
    }

    private fun onSeekFinished() {}
}
