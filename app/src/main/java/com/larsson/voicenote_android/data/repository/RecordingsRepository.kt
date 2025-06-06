package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.data.room.RecordingDao
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RecordingsRepository(private val recordingDao: RecordingDao) {

    // TODO map these to a domain model

    val TAG = "RecordingsRepo"
    fun getRecordings(): Flow<List<RecordingEntity>> {
        return recordingDao.getAllRecordings()
    }

    suspend fun addRecording(recordingEntity: RecordingEntity) {
        Log.d("recording repo", "Recording added! ${recordingEntity.recordingTitle}")
        val id = recordingDao.insertRecording(recordingEntity)
        Log.d("recording repo", id.toString())
    }

    fun getRecordingsCountPlusOne(): Flow<Int> {
        return recordingDao.getRecordingsCount().map { it + 1 }
    }

    suspend fun stopRecording(id: String, link: String, duration: String, noteId: String) {
        val dateTimeString = LocalDateTime.now().toString()

        addRecording(
            RecordingEntity(
                recordingId = id,
                recordingTitle = "Recording ${getRecordingsCountPlusOne().first()}", // TODO put in strings folder÷
                recordingLink = link,
                recordingDate = dateTimeString,
                recordingDuration = duration,
                noteId = noteId
            )
        )

    }

    fun getRecordingById(id: String): Flow<RecordingEntity> {
        return recordingDao.getRecording(id)
    }

    fun updateRecording(recordingEntity: RecordingEntity) {
        recordingDao.updateRecording(recordingEntity)
    }

    fun deleteRecording(recordingEntity: RecordingEntity) {
        recordingDao.deleteRecording(recordingEntity)
    }

    fun getRecordingsTiedToNoteById(id: String): Flow<List<RecordingEntity>> {
        return recordingDao.getRecordingsTiedToNoteById(id = id)
    }
}

// TODO Put in domain module
data class Recording(
    val title: String,
    val link: String,
    val date: String,
    val duration: String,
    val id: String,
)