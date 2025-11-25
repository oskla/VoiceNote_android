package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.toRecordingEntity
import com.larsson.voicenote_android.data.entity.toRecordings
import com.larsson.voicenote_android.data.room.RecordingDao
import com.larsson.voicenote_android.features.audiorecorder.Recorder
import com.larsson.voicenote_android.helpers.getUUID
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class RecordingsRepository(
    private val recordingDao: RecordingDao,
    private val recorder: Recorder,
) {
    val TAG = "RecordingsRepo"

    fun getRecordings(): Flow<List<Recording>> {
        return recordingDao.getAllRecordings().map { it.toRecordings() }
    }

    suspend fun addRecording(recording: Recording) {
        Log.d(
            "recording repo",
            "Recording added! Id: ${recording.id}, number: ${recording.recordingNumber}"
        )
        recordingDao.insertRecording(recording.toRecordingEntity())
    }

    fun getNextRecordingsCount(): Flow<Int> {
        return recordingDao.getNumberOfRecordings().map { it?.plus(1) ?: 1 }
    }

    fun startRecording() {
        val id = getUUID()
        recorder.startRecording(id = id)
    }

    suspend fun stopRecording(noteId: String?) {
        recorder.stop()

        val currentAudioFile = recorder.getCurrentAudioFile() ?: throw IllegalStateException("No audio file available")
        val recordingId = recorder.getCurrentRecordingId() ?: throw NullPointerException("Tried to add recording without id")

        addRecording(
            recording = Recording(
                userTitle = null,
                link = currentAudioFile.path.toString(),
                date = LocalDateTime.now().toString(),
                duration = recorder.getMetadataDuration(),
                id = recordingId,
                noteId = noteId,
                recordingNumber = getNextRecordingsCount().firstOrNull() ?: 1,
            ),
        )
    }


//    fun getRecordingById(id: String): Flow<RecordingEntity> {
//        return recordingDao.getRecording(id)
//    }
//
//    fun updateRecording(recordingEntity: RecordingEntity) {
//        recordingDao.updateRecording(recordingEntity)
//    }

    suspend fun deleteRecording(recordingId: String) {
        // Get the recording to retrieve the file path
        val recording = recordingDao.getRecording(recordingId).firstOrNull()

        // Delete the physical file if it exists
        if (recording != null) {
            Log.d(TAG, "deleting file!")
            recorder.deleteRecording(recording.recordingFileName)
        } else {
            Log.d(TAG, "recording was null!")

        }

        // Delete from database
        recordingDao.deleteRecording(recordingId)
    }

    fun getRecordingsTiedToNoteById(id: String): Flow<List<Recording>> {
        return recordingDao.getRecordingsTiedToNoteById(id = id).map { it.toRecordings() }
    }
}

data class Recording(
    val userTitle: String?,
    val link: String,
    val date: String,
    val duration: String,
    val id: String,
    val noteId: String?,
    val recordingNumber: Int,
//    val fileName: String,
) {
    val fileName = "$id.m4a"
}