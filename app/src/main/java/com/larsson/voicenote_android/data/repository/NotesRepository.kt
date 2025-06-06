package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.room.NoteDao

class NotesRepository(private val noteDao: NoteDao) {

    fun getNotes(): MutableList<NoteEntity> {
        return noteDao.getAllNotes()
    }

    suspend fun addNote(noteEntity: NoteEntity) {
        Log.d("note repo", "Note added! ${noteEntity.noteTitle}")
        noteDao.insertNote(noteEntity)
    }

    fun getNoteById(id: String): NoteEntity {
        return noteDao.getNote(id)
    }

    suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.updateNote(noteEntity)
    }

    suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}
