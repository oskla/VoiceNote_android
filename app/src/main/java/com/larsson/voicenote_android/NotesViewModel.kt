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

    private var notes = mutableStateListOf<Note>()
    private var currentNote: Note? by  mutableStateOf(null)

    fun createNote(id: String, title: String, txtContent: String) {
        notes.add(Note(id, title, txtContent ))
    }

    fun addNotes() {
        for (i in 1..10) {
           val newNote = Note(getUUID(),"title$i","Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique." )
            notes.add(newNote)
        }
    }
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