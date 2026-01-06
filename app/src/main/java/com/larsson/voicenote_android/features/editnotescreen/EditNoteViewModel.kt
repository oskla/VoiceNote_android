package com.larsson.voicenote_android.features.editnotescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.NotesRepository
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import com.larsson.voicenote_android.helpers.getUUID
import com.larsson.voicenote_android.models.NoteId
import java.time.LocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val recordingsRepository: RecordingsRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val noteStateFlow = MutableStateFlow<NoteState>(NoteState.Uninitialized)
    private val recordingsTiedToNoteStateFlow = MutableStateFlow<List<Recording>>(emptyList())

    val editNoteUiStateFlow = noteStateFlow.combine(recordingsTiedToNoteStateFlow) { noteState, recordingsTiedToNote ->

        when (noteState) {
            NoteState.Draft -> UiStateEditNote.WithData.Draft(UiNote.emptyNote)
            is NoteState.Loaded -> UiStateEditNote.WithData.Edit(
                uiNote = UiNote(
                    title = noteState.note.title,
                    textContent = noteState.note.textContent,
                    id = noteState.note.id,
                    date = noteState.note.date,
                    recordingsTiedToNote = recordingsTiedToNote
                )
            )

            NoteState.Uninitialized -> UiStateEditNote.Loading
        }
    }.stateIn(viewModelScope, WhileSubscribed(), UiStateEditNote.Loading)

    fun setCurrentNote(noteId: NoteId?) {
        if (noteId != null) {
            getNoteFromRoomById(noteId)
            collectRecordingsTiedToNoteById(noteId)
        } else {
            // This will force a draft mode in the combine
            noteStateFlow.value = NoteState.Draft
        }
    }

    fun getNoteFromRoomById(noteId: NoteId) {
        viewModelScope.launch {
            notesRepository.getNoteById(noteId).collect { note ->
                if (note != null) {
                    noteStateFlow.value = NoteState.Loaded(note)
                }
            }
        }
    }

    private fun collectRecordingsTiedToNoteById(noteId: NoteId) {
        viewModelScope.launch {
            recordingsRepository.getRecordingsTiedToNoteById(noteId).collect {
                recordingsTiedToNoteStateFlow.value = it

            }
        }
    }

    fun updateNote(title: String, txtContent: String, id: String?) {
        // Dont store to DB if the whole note is empty
        if (title.isBlank() && txtContent.isBlank()) {
            return
        }
        viewModelScope.launch {
            if (id != null) {
                notesRepository.updateNote(
                    Note(
                        title = title,
                        textContent = txtContent,
                        id = id,
                        date = LocalDateTime.now().toString()
                    )
                )

            } else { // meaning this was created through draft mode
                notesRepository.addNote(
                    Note(
                        title = title,
                        textContent = txtContent,
                        id = getUUID(),
                        date = LocalDateTime.now().toString()
                    )
                )
            }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch {
            notesRepository.deleteNoteById(id)
        }
    }
}

private sealed class NoteState {
    object Uninitialized : NoteState()
    object Draft : NoteState()
    data class Loaded(val note: Note) : NoteState()
}

sealed interface UiStateEditNote {
    data object Loading : UiStateEditNote

    sealed class WithData(open val uiNote: UiNote) : UiStateEditNote {
        data class Draft(override val uiNote: UiNote) : WithData(uiNote)
        data class Edit(override val uiNote: UiNote) : WithData(uiNote)
    }
}

data class UiNote(
    val title: String,
    val textContent: String,
    val id: String?, // can be null if in draftMode
    val date: String,
    val recordingsTiedToNote: List<Recording>
) {
    companion object {
        val emptyNote = UiNote(title = "", textContent = "", id = null, date = LocalDateTime.now().toString(), recordingsTiedToNote = emptyList())
    }
}