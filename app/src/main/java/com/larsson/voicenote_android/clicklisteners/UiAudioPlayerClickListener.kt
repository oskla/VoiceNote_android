package com.larsson.voicenote_android.clicklisteners

import com.larsson.voicenote_android.data.repository.Recording

internal interface UiAudioPlayerClickListener {
    fun onClickPlay(recording: Recording)
    fun onClickPause()
    fun onSeekTo(position: Float)
    fun onToggleExpandContainer(recordingId: String)
    fun onClickDelete(recordingId: String)
}

internal val previewAudioPlayerClickListener = object : UiAudioPlayerClickListener {
    override fun onClickPlay(recording: Recording) {}
    override fun onClickPause() {}
    override fun onSeekTo(position: Float) {}
    override fun onToggleExpandContainer(recordingId: String) {}
    override fun onClickDelete(recordingId: String) {}
}