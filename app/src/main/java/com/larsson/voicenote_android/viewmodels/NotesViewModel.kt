package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.larsson.voicenote_android.data.*

class NotesViewModel: ViewModel() {

    var editNoteVisible by mutableStateOf(false)
    var newNoteVisible by mutableStateOf(false)
    var notesListVisible by mutableStateOf(true)
    var bottomBoxVisible by mutableStateOf(true)
    var topToggleBarVisible by mutableStateOf(true)

    private var notes = mutableStateListOf<Note>()

    fun createNote(title: String, txtContent: String) {
        notes.add(Note(Note.generateId(), title, txtContent ))
    }

    fun getNoteById(id: String): Note {
        return notes.first { it.id.toString() == id }
    }

    fun getAllNotes(): () -> List<Note> {
        return  { notes.toList() }
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