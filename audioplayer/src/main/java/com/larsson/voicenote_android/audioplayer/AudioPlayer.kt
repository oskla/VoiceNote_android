package com.larsson.voicenote_android.audioplayer

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    val currentPosition: StateFlow<Position>
    val currentMediaItem: StateFlow<MediaItem?>
    val currentPlaybackState: StateFlow<PlaybackState>

    fun play()
    fun pause()
    fun stop()
    fun release()
    fun seekTo(position: Long)
    fun prepare(recordingId: String)
}

data class Position(
    val currentPosition: Long,
    val id: String,
)