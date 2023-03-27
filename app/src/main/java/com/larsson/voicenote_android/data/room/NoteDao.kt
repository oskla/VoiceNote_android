package com.larsson.voicenote_android.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.larsson.voicenote_android.data.entity.NOTES_TABLE
import com.larsson.voicenote_android.data.entity.NoteEntity

@Dao
interface NoteDao {
    @Query(value = "SELECT * FROM $NOTES_TABLE ORDER BY noteId DESC")
    suspend fun getAllNotes(): MutableList<NoteEntity>

    @Query("SELECT * FROM $NOTES_TABLE WHERE noteId LIKE :id")
    suspend fun getNote(id: Int): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}
