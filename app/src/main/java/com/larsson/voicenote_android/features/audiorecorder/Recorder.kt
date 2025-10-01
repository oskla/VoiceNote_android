package com.larsson.voicenote_android.features.audiorecorder

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File

class Recorder(private val context: Context) : AudioRecorder {

    private val TAG = "Recorder"

    private var recorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var metadataDuration: String? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder() // Deprecated but the new media recorder won't run on older devices
        }
    }

    fun getMetadataDuration(): String {
        if (metadataDuration != null) {
            return metadataDuration as String
        }
        return "0"
    }

    fun startRecording(fileName: String): File {
        val file = File(context.cacheDir, fileName)
        Log.d(TAG, "fileName: $fileName")
        start(file)
        return file
    }

    override fun start(outputFile: File) {
        val newRecorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder?.setAudioEncodingBitRate(128000)
            recorder?.setAudioSamplingRate(44100)
            setOutputFile(outputFile.absolutePath)

            prepare()
            start()

            recorder = this
        }
        recorder = newRecorder
        audioFile = outputFile
    }

    override fun stop() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recorder: ${e.message}")
        } finally {
            recorder = null
        }

        Log.d(TAG, "path: ${audioFile?.path}")
        fetchMetaDataDuration()
    }

    private fun fetchMetaDataDuration() {
        val metadataRetriever = MediaMetadataRetriever()
        try {
            metadataRetriever.setDataSource("${context.cacheDir}/${audioFile?.name}")
            metadataDuration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        } catch (e: Exception) {
            Log.e(TAG, "Error setting dataSource when retrieving metadata. Error: ${e.message}")
        }
    }


    fun deleteRecording(fileName: String) {
        File(context.cacheDir, fileName).delete()
    }

    override fun pause() {
        recorder?.pause()
    }

    override fun resumeRecording() {
        recorder?.resume()
    }
}
