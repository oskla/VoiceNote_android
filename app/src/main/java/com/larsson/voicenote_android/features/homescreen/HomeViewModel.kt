package com.larsson.voicenote_android.features.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.helpers.getUUID
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val TAG = "HomeViewModel"

class HomeViewModel(
    val notesRepository: NotesRepository,
    private val recordingsRepository: RecordingsRepository
) : ViewModel() {
    private val _notesStateFlow = MutableStateFlow<List<Note>>(emptyList())
    val notesStateFlow = _notesStateFlow.asStateFlow()

    private val _recordings = MutableStateFlow<List<Recording>>(emptyList())
    val recordings: StateFlow<List<Recording>> = _recordings

    init {
        collectAllNotes()
        collectAllRecordnings()
    }

    private fun collectAllRecordnings() {
        viewModelScope.launch {
            recordingsRepository.getRecordings().collect { recording ->
                _recordings.value = recording
            }
        }
    }

    private fun collectAllNotes() {
        viewModelScope.launch {
            notesRepository.getNotes().collect {
                _notesStateFlow.value = it
            }
        }
    }

    fun saveNote(title: String, txtContent: String): Note {
        val id = getUUID()
        val newNote = Note(
            title = title,
            textContent = txtContent,
            id = id,
            date = LocalDateTime.now().toString()
        )
        viewModelScope.launch {
            notesRepository.addNote(newNote)
        }
        Log.d(TAG, "NoteId on Note: $id")

        return newNote
    }
}