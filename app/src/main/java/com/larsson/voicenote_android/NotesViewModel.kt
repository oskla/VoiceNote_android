package com.larsson.voicenote_android

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.larsson.voicenote_android.data.*
import com.larsson.voicenote_android.ui.NoteItem

class NotesViewModel: ViewModel() {

    private var notes = mutableStateListOf(note1, note2, note3, note4, note5, note6, note7, note8, note9, note10)
    private var currentNote: Note? by  mutableStateOf(null)

    fun getNoteById(id: String): Note {
        return notes.first { it.id.toString() == id }
    }

    fun getAllNotes(): () -> List<Note> {
        return  { notes.toList() }
    }

    fun setToCurrentNote(selectedNote: Note) {
       this.currentNote = selectedNote
    }

    fun saveNote(title: String, txtContent: String, id: String) {
        val note = getNoteById(id)
        note.title = title
        note.txtContent = txtContent
        Log.d("SaveNote", "${note.title} + ${note.txtContent}")
        Log.d("SaveNote", "$title + $txtContent")
    }


}