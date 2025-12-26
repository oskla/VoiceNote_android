package com.larsson.voicenote_android.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.larsson.voicenote_android.data.entity.RECORDINGS_TABLE
import com.larsson.voicenote_android.data.entity.RecordingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {

    @Query(value = "SELECT * FROM $RECORDINGS_TABLE ORDER BY recording_date DESC")
    fun getAllRecordings(): Flow<List<RecordingEntity>>

    @Query("SELECT * FROM $RECORDINGS_TABLE WHERE recordingId LIKE :id")
    fun getRecording(id: String): Flow<RecordingEntity>

    @Query(
        "SELECT * FROM $RECORDINGS_TABLE WHERE RECORDINGS_TABLE.noteId LIKE :id",
    )
    fun getRecordingsTiedToNoteById(id: String): Flow<List<RecordingEntity>>

    @Query("SELECT COUNT(*) FROM $RECORDINGS_TABLE")
    fun getRecordingsCount(): Flow<Int>

    // Used for naming e.g "Recording 7", basically just counting items without userTitle
    @Query("SELECT MAX(recording_number) FROM $RECORDINGS_TABLE WHERE recording_title IS NULL ORDER BY recording_number ASC")
    fun getNumberOfRecordings(): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecording(recordingEntity: RecordingEntity): Long // can return ID

    @Update
    fun updateRecording(recordingEntity: RecordingEntity)

    @Query("UPDATE $RECORDINGS_TABLE SET recording_title = :title WHERE recordingId = :id")
    suspend fun updateRecordingTitle(id: String, title: String)

    @Query("DELETE FROM $RECORDINGS_TABLE WHERE recordingId =:recordingId")
    suspend fun deleteRecording(recordingId: String)
}
