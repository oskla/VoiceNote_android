package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.helpers.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.util.UUID

class RecordingViewModel(private val recorder: Recorder, private val recordingsRepo: RecordingsRepository) : ViewModel() {

    val TAG = "RecordingViewModel"
    var recordingsState = mutableStateListOf<RecordingEntity>()

    private var audioFile: File? = null
    fun startRecording() {
        recorder.startRecording(fileName = getUUID()).also {// TODO fileName was an issue before. Find a way to increment ++ in name (both in Room and in cacheDir)
            audioFile = it
        }
    }
    suspend fun getAllRecordingsRoom(): List<RecordingEntity> {
        var allRecordings: MutableList<RecordingEntity>
        withContext(Dispatchers.IO) {
            allRecordings = recordingsRepo.getRecordings()
        }
        Log.d("Recording Room", allRecordings.toList().toString())
        recordingsState = allRecordings.toMutableStateList()
        return allRecordings
    }

    private suspend fun getRecordingByIdRoom(id: String) {
        recordingsRepo.getRecordingById(id)
    }

    suspend fun stopRecording() {
        val id = UUID.randomUUID().toString()
        val dateTimeString = LocalDateTime.now().toString()
        recorder.stop()

        recordingsRepo.addRecording(
            RecordingEntity(
                recordingTitle = DateFormatter(dateTimeString).getFormattedTime(),
                recordingId = id,
                recordingLink = audioFile?.path.toString(),
                recordingDate = dateTimeString,
                recordingDuration = recorder.getMetaData(),
            ),
        )

        getRecordingByIdRoom(id)
        getAllRecordingsRoom()
    }

    fun updateFileNameRecording() {
    }
}
