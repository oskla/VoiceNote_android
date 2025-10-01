package com.larsson.voicenote_android.viewmodels.interfaces

sealed interface AudioPlayerEvent {
    data class Play(val recordingId: String) : AudioPlayerEvent
    object Pause : AudioPlayerEvent
    object SetToIdle : AudioPlayerEvent
    data class SeekTo(val position: Int) : AudioPlayerEvent
    data object OnSeekFinished : AudioPlayerEvent
    data class ToggleExpanded(val shouldExpand: Boolean, val recordingId: String) : AudioPlayerEvent
}
