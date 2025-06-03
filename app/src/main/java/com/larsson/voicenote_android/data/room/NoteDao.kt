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
    @Query(value = "SELECT * FROM $NOTES_TABLE ORDER BY date DESC")
    fun getAllNotes(): MutableList<NoteEntity>

    @Query("SELECT * FROM $NOTES_TABLE WHERE noteId LIKE :id")
    fun getNote(id: String): NoteEntity

    @Query("DELETE FROM $NOTES_TABLE")
    fun deleteAllNotes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity): Long

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)
}
