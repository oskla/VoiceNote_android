package com.larsson.voicenote_android.features.audiorecorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Recorder(private val context: Context) : AudioRecorder {

    private val TAG = "Recorder"

    private var recorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var metaData: String? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder() // Deprecated but the new media recorder won't run on older devices
        }
    }

    fun getMetaData(): String {
        if (metaData != null) {
            return metaData as String
        }
        return "0"
    }

    fun startRecording(fileName: String): File {
        return File(context.cacheDir, fileName).also {
            recorder?.start()
            audioFile = it
            Log.d(TAG, "audioFile: $audioFile")
            Log.d(TAG, "audioFile absolute path: ${audioFile?.absolutePath}")
            start(it)
        }
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS) // TODO probably change this to something of higher quality
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // TODO check this too in terms of quality

            recorder?.setAudioEncodingBitRate(128000)
            recorder?.setAudioSamplingRate(44100)
            Log.d(TAG, "hay")
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override suspend fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null

        Log.d(TAG, "pathhh: ${audioFile?.path}")
        fetchMetaData()

        Log.d(TAG, "Metadata: $metaData")
    }
    private suspend fun fetchMetaData() {
        withContext(Dispatchers.IO) {
            val metadataRetriever = MediaMetadataRetriever()
            try {
                metadataRetriever.setDataSource("${context.cacheDir}/${audioFile?.name}")
                metaData = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            } catch (e: Exception) {
                Log.e(TAG, "Error setting dataSource when retrieving metadata. Error: ${e.message}")
            }
        }
    }

    fun deleteRecording(fileName: String) {
        File(context.cacheDir, fileName).delete()
    }

    override fun pause() {
        recorder?.pause()
    }

    override fun resumePlaying() {
        recorder?.resume()
    }
}
