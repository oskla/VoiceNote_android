package com.larsson.voicenote_android.clicklisteners

internal interface UiAudioPlayerClickListener {
    fun onClickPlay(recordingId: String)
    fun onClickPause()
    fun onSeekTo(position: Float)
    fun onSeekingFinished()
    fun onToggleExpandContainer(recordingId: String)
    fun onClickDelete(recordingId: String)
}

internal val previewAudioPlayerClickListener = object : UiAudioPlayerClickListener {
    override fun onClickPlay(recordingId: String) {}
    override fun onClickPause() {}
    override fun onSeekTo(position: Float) {}
    override fun onSeekingFinished() {}
    override fun onToggleExpandContainer(recordingId: String) {}
    override fun onClickDelete(recordingId: String) {}
}