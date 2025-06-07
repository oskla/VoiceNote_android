package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.room.NoteDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NoteDao) {

    fun getNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    suspend fun addNote(noteEntity: NoteEntity) {
        noteDao.insertNote(noteEntity)
        Log.d("note repo", "Note added! ${noteEntity.noteTitle}")
    }

    fun getNoteById(id: String): Flow<NoteEntity> {
        return noteDao.getNote(id)
    }

    suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.updateNote(noteEntity)
    }

    suspend fun deleteNoteById(id: String) {
        noteDao.deleteNoteById(id)
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}
