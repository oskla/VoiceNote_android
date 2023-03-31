package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.repository.NotesRepository
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(val dbRepo: NotesRepository) : ViewModel() {

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

    fun addNoteToRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            dbRepo.addNote(NoteEntity(noteTitle = title, noteTxtContent = txtContent, noteId = id.toString(), date = LocalDateTime.now().toString()))
        }
    }

    fun updateNoteRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            dbRepo.updateNote(NoteEntity(noteTitle = title, noteTxtContent = txtContent, noteId = id, date = LocalDateTime.now().toString()))
        }
    }

    suspend fun deleteAllNotesRoom() {
        dbRepo.deleteAllNotes()
    }
}
