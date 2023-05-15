package com.larsson.voicenote_android.viewmodels.interfaces

sealed interface AudioPlayerEvent {
    data class Play(val recordingId: String) : AudioPlayerEvent
    object Pause : AudioPlayerEvent
    object SetToIdle : AudioPlayerEvent
    object SetToComplete : AudioPlayerEvent
}
// TODO add seekTo?
