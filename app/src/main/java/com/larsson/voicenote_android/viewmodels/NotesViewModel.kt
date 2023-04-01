package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class NotesViewModel(val dbRepo: NotesRepository) : ViewModel() {

    private val TAG = "NotesViewModel"
    var notesListVisible by mutableStateOf(true)
    var recordingsListVisible by mutableStateOf(false)
    private var selectedNoteId by mutableStateOf("")

    suspend fun getAllNotesFromRoom(): List<NoteEntity> {
        var allNotes: MutableList<NoteEntity>
        withContext(Dispatchers.IO) {
            allNotes = dbRepo.getNotes()
        }
        return allNotes
    }

    suspend fun getNoteFromRoomById(id: String): NoteEntity {
        var selectedNote: NoteEntity
        withContext(Dispatchers.IO) {
            selectedNote = dbRepo.getNoteById(id)
            Log.d("note room vm", "${selectedNote.noteTitle} and ${selectedNote.noteTxtContent},${selectedNote.noteId},")
        }
        return selectedNote
    }

    fun addNoteToRoom(title: String, txtContent: String): NoteEntity {
        val id = getUUID()
        val newNote = NoteEntity(noteTitle = title, noteTxtContent = txtContent, noteId = id.toString(), date = LocalDateTime.now().toString())
        viewModelScope.launch {
            dbRepo.addNote(newNote)
        }
        Log.d(TAG, "NoteId on Note: $id")

        return newNote
    }

    fun updateNoteRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            dbRepo.updateNote(NoteEntity(noteTitle = title, noteTxtContent = txtContent, noteId = id, date = LocalDateTime.now().toString()))
        }
    }
    suspend fun deleteNoteByIdRoom(id: String) {
        getNoteFromRoomById(id).also {
            deleteNoteRoom(it)
        }
    }
    private fun deleteNoteRoom(note: NoteEntity) {
        viewModelScope.launch {
            dbRepo.deleteNote(note)
        }
    }

    suspend fun deleteAllNotesRoom() {
        dbRepo.deleteAllNotes()
    }
}
