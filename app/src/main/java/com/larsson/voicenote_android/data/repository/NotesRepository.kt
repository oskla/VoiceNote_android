package com.larsson.voicenote_android.data.repository // ktlint-disable package-name

import android.util.Log
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.room.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepository(private val noteDao: NoteDao) {

    fun getNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { it.toNotes() }
    }

    suspend fun addNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
        Log.d("note repo", "Note added! ${note.title}")
    }

    fun getNoteById(id: String): Flow<Note?> {
        return noteDao.getNote(id).map {
            Log.d("osk", "noteEntity: $it")
            it?.toNote()
        }
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toNoteEntity())
    }

    suspend fun deleteNoteById(id: String) {
        noteDao.deleteNoteById(id)
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}

fun NoteEntity.toNote(): Note {
    Log.d("osk", "noteID: $noteId, title: $noteTitle, text content: $noteTxtContent date: $date")
    return Note(
        id = noteId,
        title = noteTitle,
        textContent = noteTxtContent,
        date = date
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        noteId = id,
        noteTitle = title,
        noteTxtContent = textContent,
        date = date
    )
}

fun List<NoteEntity>.toNotes(): List<Note> {
    return this.map { noteEntity ->
        Note(
            id = noteEntity.noteId,
            title = noteEntity.noteTitle,
            textContent = noteEntity.noteTxtContent,
            date = noteEntity.date
        )
    }
}

data class Note(
    val id: String,
    val title: String,
    val textContent: String,
    val date: String,
)
