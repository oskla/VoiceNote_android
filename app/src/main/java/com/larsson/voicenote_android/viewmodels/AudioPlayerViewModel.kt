package com.larsson.voicenote_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.audioplayer.AudioPlayer
import com.larsson.voicenote_android.audioplayer.PlaybackState
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AudioPlayerViewModel(
    private val audioPlayer: AudioPlayer,
    private val recordingRepository: RecordingsRepository,
) : ViewModel() {
    private val TAG = "AudioPlayerViewModel"

    val isPlaying = audioPlayer.currentPlaybackState
        .map { it == PlaybackState.Playing }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val currentPosition: StateFlow<Long> = audioPlayer.currentPosition
        .map { it.currentPosition }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0L)

    private val _expandedRecordingId = MutableStateFlow("")
    val expandedRecordingId = _expandedRecordingId.asStateFlow()

    fun handleUIEvents(event: AudioPlayerEvent) {
        when (event) {
            AudioPlayerEvent.Pause -> pause()
            is AudioPlayerEvent.Play -> play(recording = event.recording)
            AudioPlayerEvent.SetToIdle -> {}
            is AudioPlayerEvent.SeekTo -> seekTo(position = event.position)
            AudioPlayerEvent.OnSeekFinished -> onSeekFinished()
            is AudioPlayerEvent.ToggleExpanded -> toggleExpanded(event)
            is AudioPlayerEvent.Delete -> deleteRecording(event.recordingId)
        }
    }


    private fun toggleExpanded(event: AudioPlayerEvent.ToggleExpanded) {
        if (recordingIdAlreadyExpanded(event)) {
            resetPlayback()
        } else {
            if (recordingIdCollapsed()) {
                resetPlayback()
            }
            _expandedRecordingId.value = event.recordingId
        }
    }

    private fun play(recording: Recording) {
        if (audioPlayer.currentMediaItem.value?.mediaId != recording.id) {
            audioPlayer.prepare(recording.fileName, recording.id)
        }
        audioPlayer.play()
    }

    private fun pause() {
        audioPlayer.pause()
    }

    private fun seekTo(position: Float) {
        audioPlayer.seekTo(position.toLong())
    }

    private fun stop() {
        audioPlayer.stop()
    }

    private fun deleteRecording(id: String) {
        viewModelScope.launch {
            recordingRepository.deleteRecording(id)
        }
    }

    override fun onCleared() {
        audioPlayer.release()
        super.onCleared()
    }

    private fun onSeekFinished() {}

    private fun recordingIdAlreadyExpanded(event: AudioPlayerEvent.ToggleExpanded) = event.recordingId == _expandedRecordingId.value
    private fun recordingIdCollapsed() = expandedRecordingId.value.isNotBlank()
    private fun resetPlayback() {
        _expandedRecordingId.value = ""
        pause()
        seekTo(0F)
    }
}
