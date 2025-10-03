package com.larsson.voicenote_android.audioplayer

import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    val currentPosition: StateFlow<Long>
    val currentMediaItem: StateFlow<String?>
    val currentPlaybackState: StateFlow<PlaybackState>

    fun play()
    fun pause()
    fun stop()
    fun release()
    fun seekTo(position: Long)
    fun prepare(recordingId: String)
}