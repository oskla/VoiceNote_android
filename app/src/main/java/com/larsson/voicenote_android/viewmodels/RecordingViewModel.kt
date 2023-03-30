package com.larsson.voicenote_android.viewmodels

import androidx.lifecycle.ViewModel
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import java.time.LocalDateTime

class RecordingViewModel(private val recorder: Recorder) : ViewModel() {

    fun startRecording() {
        recorder.startRecording(LocalDateTime.now().toString())
    }

    fun stopRecording() {
        recorder.stop()
    }
}
