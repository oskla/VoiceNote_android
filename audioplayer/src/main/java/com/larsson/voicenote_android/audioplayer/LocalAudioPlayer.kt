package com.larsson.voicenote_android.audioplayer

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LocalAudioPlayer(
    private val context: Context
) : AudioPlayer {
    private val _currentPosition = MutableStateFlow(0L)
    override val currentPosition: StateFlow<Long> = _currentPosition

    private val _currentMediaItem = MutableStateFlow<String?>(null)
    override val currentMediaItem: StateFlow<String?> = _currentMediaItem

    private val _currentPlaybackState = MutableStateFlow(PlaybackState.Idle)
    override val currentPlaybackState: StateFlow<PlaybackState> = _currentPlaybackState

    private var controller: MediaController? = null

    private var positionJob: Job? = null

    init {
        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                controller = controllerFuture.get()
                controller?.addListener(controllerListener)
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun mapPlayerState(playbackState: Int?, isPlaying: Boolean) {
        _currentPlaybackState.value = when {
            isPlaying -> PlaybackState.Playing
            playbackState == Player.STATE_BUFFERING -> PlaybackState.Buffering
            playbackState == Player.STATE_IDLE -> PlaybackState.Idle
            playbackState == Player.STATE_ENDED -> PlaybackState.Ended
            playbackState == Player.STATE_READY -> PlaybackState.Ready
            else -> currentPlaybackState.value
        }
    }

    private val controllerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            mapPlayerState(controller?.playbackState, isPlaying = isPlaying)
            if (isPlaying) startUpdatingPosition() else stopUpdatingPosition()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            mapPlayerState(
                playbackState = playbackState,
                isPlaying = currentPlaybackState.value == PlaybackState.Playing
            )
        }
    }

    private fun startUpdatingPosition() {
        positionJob?.cancel()
        positionJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                val position = controller?.currentPosition
                if (position != null) {
                    _currentPosition.value = position
                    delay(200)
                }
            }
        }
    }

    private fun stopUpdatingPosition() {
        positionJob?.cancel()
        positionJob = null
    }


    private fun setMediaItem(recordingId: String) {
        val file = File(context.cacheDir, recordingId)
        val uri = Uri.fromFile(file)
        controller?.setMediaItem(MediaItem.fromUri(uri))
    }

    override fun prepare(recordingId: String) { // TODO call this when toggling on a recording (not when pressing play)
        setMediaItem(recordingId)
        controller?.prepare()
    }

    override fun play() {
        controller?.play()
    }

    override fun pause() {
        controller?.pause()
    }

    override fun seekTo(position: Long) {
        controller?.seekTo(position)
    }

    override fun stop() {
        controller?.stop()
        controller?.release()
        controller = null
    }

}

enum class PlaybackState {
    Idle,
    Ready,
    Playing,
    Buffering,
    Ended,
    Error
}