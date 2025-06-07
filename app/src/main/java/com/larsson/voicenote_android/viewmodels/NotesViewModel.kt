package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.helpers.getUUID
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// TODO make handleUiEvents
// TODO maybe use StateFlow instead of calling getNotes for example in UI
class NotesViewModel(val dbRepo: NotesRepository) : ViewModel() {

    private val TAG = "NotesViewModel"
    var notesListVisible by mutableStateOf(true)
    var recordingsListVisible by mutableStateOf(false)
    private val _notesStateFlow = MutableStateFlow<List<Note>>(emptyList())
    val notesStateFlow = _notesStateFlow.asStateFlow()
    private val _currentNoteStateFlow = MutableStateFlow<Note?>(null)
    val currentNoteStateFlow = _currentNoteStateFlow.asStateFlow()

    init {
        getAllNotesFromRoom()
    }

    private fun getAllNotesFromRoom() {
        viewModelScope.launch {
            dbRepo.getNotes().collect {
                _notesStateFlow.value = it
            }
        }
    }

    fun getNoteFromRoomById(id: String) {
        viewModelScope.launch {
            dbRepo.getNoteById(id).collect {
                _currentNoteStateFlow.value = it
            }
        }
    }

    fun addNoteToRoom(title: String, txtContent: String): Note {
        val id = getUUID()
        val newNote = Note(
            title = title,
            textContent = txtContent,
            id = id,
            date = LocalDateTime.now().toString()
        )
        viewModelScope.launch {
            dbRepo.addNote(newNote)
        }
        Log.d(TAG, "NoteId on Note: $id")

        return newNote
    }

    fun updateNoteRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            dbRepo.updateNote(
                Note(
                    title = title,
                    textContent = txtContent,
                    id = id,
                    date = LocalDateTime.now().toString()
                )
            )
        }
    }

    fun deleteNoteByIdRoom(id: String) {
        viewModelScope.launch {
            Log.d("osk", "deleting note id: $id")
            dbRepo.deleteNoteById(id)
        }
    }

}
