package com.larsson.voicenote_android.audioplayer

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
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

private val TAG = "AudioPlayer"

class LocalAudioPlayer(
    private val context: Context,
) : AudioPlayer {
    private val _currentPosition = MutableStateFlow(Position(0L, ""))
    override val currentPosition: StateFlow<Position> = _currentPosition

    private val _currentMediaItem = MutableStateFlow<MediaItem?>(null)
    override val currentMediaItem: StateFlow<MediaItem?> = _currentMediaItem

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
            MoreExecutors.directExecutor(),
        )
    }

    private fun mapPlayerState(
        playbackState: Int?,
        isPlaying: Boolean,
    ) {
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
                isPlaying = currentPlaybackState.value == PlaybackState.Playing,
            )
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Log.d("AudioPlayer", "onPlayerError: ${error.localizedMessage}")
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            _currentMediaItem.value = mediaItem
        }
    }

    private fun startUpdatingPosition() {
        positionJob?.cancel()
        positionJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                val position = controller?.currentPosition
                if (position != null) {
                    _currentPosition.value = Position(position, currentMediaItem.value?.mediaId ?: "")
                    delay(200)
                }
            }
        }
    }

    private fun stopUpdatingPosition() {
        positionJob?.cancel()
        positionJob = null
    }

    private fun setMediaItem(fileName: String, id: String) {
        try {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS), fileName)

            val item = MediaItem.Builder()
                .setUri(Uri.fromFile(file))
                .setMediaId(id)
                .build()

            controller?.setMediaItem(item)
        } catch (e: Exception) {
            Log.d("AudioPlayer", "setMediaItem: ${e.localizedMessage}")
        }
    }

    override fun prepare(fileName: String, id: String) {
        setMediaItem(fileName = fileName, id = id)
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
        // I need to set this here explicitly because the player will only get
        // updates while playing otherwise
        _currentPosition.value = Position(position, currentMediaItem.value?.mediaId ?: "")
    }

    override fun release() {
        stopUpdatingPosition()
        controller?.stop()
        controller?.removeListener(controllerListener)
        controller?.release()
        controller = null
    }

    override fun stop() {
        controller?.stop()
    }
}

enum class PlaybackState {
    Idle,
    Ready,
    Playing,
    Buffering,
    Ended,
    Error,
}