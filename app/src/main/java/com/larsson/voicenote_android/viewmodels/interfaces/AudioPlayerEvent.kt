package com.larsson.voicenote_android.viewmodels.interfaces

sealed interface AudioPlayerEvent {
    data class Play(val recordingId: String) : AudioPlayerEvent
    data class Delete(val recordingId: String) : AudioPlayerEvent
    object Pause : AudioPlayerEvent
    object SetToIdle : AudioPlayerEvent
    data class SeekTo(val position: Float) : AudioPlayerEvent
    data object OnSeekFinished : AudioPlayerEvent
    data class ToggleExpanded(
        val recordingId: String,
    ) : AudioPlayerEvent
}
