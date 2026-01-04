package com.larsson.voicenote_android.features.editnotescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val recordingsRepository: RecordingsRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val _currentNoteStateFlow = MutableStateFlow<Note?>(null)
    val currentNoteStateFlow = _currentNoteStateFlow.asStateFlow()

    fun getNoteFromRoomById(id: String) {
        viewModelScope.launch {
            notesRepository.getNoteById(id).collect {
                _currentNoteStateFlow.value = it
            }
        }
    }

    fun getRecordingsTiedToNoteById(id: String): Flow<List<Recording>> {
        return recordingsRepository.getRecordingsTiedToNoteById(id = id).map { it.reversed() }
    }

    fun updateNoteRoom(title: String, txtContent: String, id: String) {
        viewModelScope.launch {
            notesRepository.updateNote(
                Note(
                    title = title,
                    textContent = txtContent,
                    id = id,
                    date = LocalDateTime.now().toString()
                )
            )
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            notesRepository.deleteNoteById(id)
        }
    }
}