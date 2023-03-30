package com.larsson.voicenote_android.features.audiorecorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class Recorder(private val context: Context) : AudioRecorder {

    private val TAG = "Recorder"

    private var recorder: MediaRecorder? = null
    private var audioFile: File? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder() // Deprecated but the new media recorder won't run on older devices
        }
    }
    fun startRecording(fileName: String) {
        File(context.cacheDir, fileName).also {
            recorder?.start()
            audioFile = it
            Log.d(TAG, "audioFile: $audioFile")
        }.also {
            start(it)
            audioFile = it
            Log.d(TAG, "audioFile: $audioFile")
        }
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS) // TODO probably change this to something of higher quality
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // TODO check this too in terms of quality

            recorder?.setAudioEncodingBitRate(128000)
            recorder?.setAudioSamplingRate(44100)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()

        recorder = null
    }

    override fun pause() {
        recorder?.pause()
    }

    override fun resumePlaying() {
        recorder?.resume()
    }
}
