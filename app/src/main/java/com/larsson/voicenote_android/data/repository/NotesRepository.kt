package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.room.NoteDao

class NotesRepository(private val noteDao: NoteDao) {

    // val readAllData: MutableList<NoteEntity> = noteDao.getAllNotes()

    suspend fun getNotes(): MutableList<NoteEntity> {
        return noteDao.getAllNotes()
    }

    suspend fun addNote(noteEntity: NoteEntity) {
        Log.d("note repo", "Note added! ${noteEntity.noteTitle}")
        val id = noteDao.insertNote(noteEntity)
        Log.d("note repo", id.toString())
    }

    suspend fun getNoteById(id: String): NoteEntity {
        return noteDao.getNote(id)
    }

    suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.updateNote(noteEntity)
    }

    suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }
}
