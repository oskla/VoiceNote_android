package com.larsson.voicenote_android.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.larsson.voicenote_android.data.entity.RECORDINGS_TABLE
import com.larsson.voicenote_android.data.entity.RecordingEntity

@Dao
interface RecordingDao {

    @Query(value = "SELECT * FROM $RECORDINGS_TABLE ORDER BY recording_date DESC")
    suspend fun getAllRecordings(): MutableList<RecordingEntity>

    @Query("SELECT * FROM $RECORDINGS_TABLE WHERE recordingId LIKE :id")
    suspend fun getRecording(id: String): RecordingEntity

    @Query(
        "SELECT * FROM $RECORDINGS_TABLE WHERE RECORDINGS_TABLE.noteId LIKE :id",
    )
    suspend fun getRecordingsTiedToNoteById(id: String): MutableList<RecordingEntity>

    @Query("SELECT COUNT(*) FROM $RECORDINGS_TABLE")
    suspend fun getRecordingsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecording(recordingEntity: RecordingEntity): Long // can return ID

    @Update
    suspend fun updateRecording(recordingEntity: RecordingEntity)

    @Delete
    suspend fun deleteRecording(recordingEntity: RecordingEntity)
}
