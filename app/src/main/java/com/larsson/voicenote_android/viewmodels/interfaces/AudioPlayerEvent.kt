package com.larsson.voicenote_android.viewmodels.interfaces
import com.larsson.voicenote_android.data.repository.Recording

sealed interface AudioPlayerEvent {
    data class Play(val recording: Recording) : AudioPlayerEvent
    data class Delete(val recordingId: String) : AudioPlayerEvent
    object Pause : AudioPlayerEvent
    object SetToIdle : AudioPlayerEvent
    data class SeekTo(val position: Float) : AudioPlayerEvent
    data class ToggleExpanded(
        val recordingId: String,
    ) : AudioPlayerEvent
}
