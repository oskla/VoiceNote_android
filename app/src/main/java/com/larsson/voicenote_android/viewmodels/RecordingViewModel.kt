package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.helpers.getUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime

// TODO change name functionality
// TODO delete recording (both file and from Room)
// TODO make handleUiEvents

class RecordingViewModel(private val recorder: Recorder, private val recordingsRepo: RecordingsRepository) : ViewModel() {

    val TAG = "RecordingViewModel"

    private val _recordings = MutableStateFlow<List<RecordingEntity>>(emptyList())
    val recordings: StateFlow<List<RecordingEntity>> = _recordings
    private var audioFile: File? = null
    private var localUUID: String = ""

    fun startRecording() {
        recorder.startRecording(fileName = setLocalUUID()).also {
            audioFile = it
        }
    }

    suspend fun getAllRecordingsRoom(): List<RecordingEntity> {
        var allRecordings: MutableList<RecordingEntity>
        withContext(Dispatchers.IO) {
            allRecordings = recordingsRepo.getRecordings()
        }
        _recordings.value = allRecordings
        return allRecordings
    }

    private suspend fun getRecordingByIdRoom(id: String) {
        recordingsRepo.getRecordingById(id)
    }

    suspend fun getRecordingsTiedToNoteById(id: String): List<RecordingEntity> {
        return recordingsRepo.getRecordingsTiedToNoteById(id = id)
    }

    suspend fun stopRecording(noteId: String? = "0000") {
        val id = getLocalUUID()
        val dateTimeString = LocalDateTime.now().toString()
        recorder.stop()

        recordingsRepo.addRecording(
            RecordingEntity(
                recordingTitle = setTitleRecordingRoom(),
                recordingId = id,
                recordingLink = audioFile?.path.toString(),
                recordingDate = dateTimeString,
                recordingDuration = recorder.getMetaData(),
                noteId = noteId ?: "0000",
            ),
        )

        getRecordingByIdRoom(id)
        getAllRecordingsRoom()
        Log.d(TAG, "NoteId on Recording: $noteId")
    }

    suspend fun setTitleRecordingRoom(): String {
        val count = recordingsRepo.getRecordingsCount()
        return "Recording $count"
    }

    suspend fun deleteRecordingFile() {
    }

    fun updateFileNameRecording(recording: RecordingEntity) {
        viewModelScope.launch {
            recordingsRepo.deleteRecording(recording)
        }
    }

    private fun getLocalUUID(): String {
        return localUUID
    }

    private fun setLocalUUID(): String {
        localUUID = getUUID()
        return localUUID
    }
}
