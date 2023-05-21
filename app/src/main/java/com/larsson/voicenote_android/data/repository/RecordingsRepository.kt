package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.data.room.RecordingDao

class RecordingsRepository(private val recordingDao: RecordingDao) {

    val TAG = "RecordingsRepo"
    suspend fun getRecordings(): MutableList<RecordingEntity> {
        return recordingDao.getAllRecordings()
    }

    suspend fun addRecording(recordingEntity: RecordingEntity) {
        Log.d("recording repo", "Recording added! ${recordingEntity.recordingTitle}")
        val id = recordingDao.insertRecording(recordingEntity)
        Log.d("recording repo", id.toString())
    }

    suspend fun getRecordingById(id: String): RecordingEntity {
        Log.d(TAG, recordingDao.getRecording(id).recordingDuration)
        return recordingDao.getRecording(id)
    }

    suspend fun updateRecording(recordingEntity: RecordingEntity) {
        recordingDao.updateRecording(recordingEntity)
    }

    suspend fun deleteRecording(recordingEntity: RecordingEntity) {
        recordingDao.deleteRecording(recordingEntity)
    }

    suspend fun getRecordingsTiedToNoteById(id: String): MutableList<RecordingEntity> {
        return recordingDao.getRecordingsTiedToNoteById(id = id)
    }
}
