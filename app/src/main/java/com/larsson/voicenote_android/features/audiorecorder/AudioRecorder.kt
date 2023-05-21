package com.larsson.voicenote_android.features.audiorecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    suspend fun stop()
    fun pause()
    fun resumeRecording()
}
