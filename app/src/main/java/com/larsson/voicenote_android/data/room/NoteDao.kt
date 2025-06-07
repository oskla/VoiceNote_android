package com.larsson.voicenote_android.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.larsson.voicenote_android.data.entity.NOTES_TABLE
import com.larsson.voicenote_android.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query(value = "SELECT * FROM $NOTES_TABLE ORDER BY date DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM $NOTES_TABLE WHERE noteId LIKE :id")
    fun getNote(id: String): Flow<NoteEntity>

    @Query("DELETE FROM $NOTES_TABLE")
    fun deleteAllNotes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("DELETE FROM $NOTES_TABLE WHERE noteId = :id")
    suspend fun deleteNoteById(id: String)
}
