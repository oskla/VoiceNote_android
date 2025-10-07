package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.toRecordingEntity
import com.larsson.voicenote_android.data.entity.toRecordings
import com.larsson.voicenote_android.data.room.RecordingDao
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class RecordingsRepository(private val recordingDao: RecordingDao) {

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

    suspend fun stopRecording(id: String, link: String, duration: String, noteId: String) {
        val dateTimeString = LocalDateTime.now().toString()
        addRecording(
            recording = Recording(
                userTitle = null,
                link = link,
                date = dateTimeString,
                duration = duration,
                id = id,
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
    val noteId: String,
    val recordingNumber: Int,
)