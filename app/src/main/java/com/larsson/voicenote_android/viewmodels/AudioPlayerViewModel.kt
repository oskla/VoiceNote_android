package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.larsson.voicenote_android.MediaManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AudioPlayerViewModel(private val mediaManager: MediaManager) : ViewModel() {
   private val TAG = "AudioPlayerViewModel"

    private val _playingState = MutableStateFlow<PlayingState>(PlayingState.Idle)
    val playingState: StateFlow<PlayingState>
        get() = _playingState

    fun play(audioPath: String) {
        Log.d(TAG, audioPath)
        mediaManager.start(audioPath)
        _playingState.value = PlayingState.Playing

        // mediaManager.setDataSource()
    }

    sealed class PlayingState {
        object Idle : PlayingState()
        object Playing : PlayingState()
        object Paused : PlayingState()
        object Completed : PlayingState()
        data class Error(val throwable: Throwable?) : PlayingState()
    }
}
