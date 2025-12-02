package com.larsson.voicenote_android.features.audiorecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
    fun pause()
    fun resumeRecording()
}
