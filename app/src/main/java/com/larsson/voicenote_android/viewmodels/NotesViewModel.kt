package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(val dbRepo: NotesRepository) : ViewModel() {

    var editNoteVisible by mutableStateOf(false)
    var newNoteVisible by mutableStateOf(false)
    var notesListVisible by mutableStateOf(true)
    var recordingsListVisible by mutableStateOf(false)
    var bottomBoxVisible by mutableStateOf(true)
    var topToggleBarVisible by mutableStateOf(true)
    private var selectedNoteId by mutableStateOf("")

    private var _notes = mutableStateListOf<Note>(
        Note("3", "Hej", "asg"),
        Note("2", "Hsfej", "fsasg"),
    )
    val notes: List<Note> = _notes

    fun createNote(title: String, txtContent: String) {
        _notes.add(
            Note(
                "4",
                title = title,
                txtContent = txtContent,
            ),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllNotesFromRoom(): List<NoteEntity> {
        var allNotes = mutableListOf<NoteEntity>()
        withContext(Dispatchers.IO) {
            allNotes = dbRepo.getNotes()
            Log.d("note room vm ALL notes", "$allNotes")
        }
        return allNotes
    }

    fun getNoteFromRoomById(id: Int) {
        viewModelScope.launch {
            val noteEntity = dbRepo.getNoteById("6")
            Log.d("note room vm", "${noteEntity.noteTitle} and ${noteEntity.noteTxtContent},${noteEntity.noteId},")
        }
    }

    fun addNoteToRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            dbRepo.addNote(NoteEntity(noteTitle = title, noteTxtContent = txtContent, noteId = id.toString()))
        }
    }

    fun selectNoteById(id: String) {
        selectedNoteId = notes.first { it.id == id }.id
    }

    fun getSelectedNote(): Note {
        Log.d("selected note GET", selectedNoteId)
        return getNoteById(selectedNoteId)
    }

    fun getNoteById(id: String): Note {
        return notes.first { it.id == id }
    }

    fun getAllNotes(): List<Note> {
        return notes
    }

    fun saveNote(title: String, txtContent: String, id: String) {
        val note = getNoteById(id)
        note.title = title
        note.txtContent = txtContent
        Log.d("SaveNote", "${note.title} + ${note.txtContent}")
        Log.d("SaveNote", "$title + $txtContent")
    }

    fun visibilityModifier(
        homeScreen: Boolean,
    ) {
        if (homeScreen) {
            editNoteVisible = false
            notesListVisible = true
            bottomBoxVisible = true
            newNoteVisible = false
            topToggleBarVisible = true
        } else {
            editNoteVisible = true
            notesListVisible = false
            bottomBoxVisible = false
            newNoteVisible = true
            topToggleBarVisible = false
        }
    }
}
