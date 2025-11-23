package com.larsson.voicenote_android.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// TODO change name functionality
// TODO delete recording (both file and from Room)
// TODO make handleUiEvents

class RecordingViewModel(
    private val recordingsRepo: RecordingsRepository
) : ViewModel() {

    val TAG = "RecordingViewModel"

    private val _recordings = MutableStateFlow<List<Recording>>(emptyList())
    val recordings: StateFlow<List<Recording>> = _recordings

    init {
        viewModelScope.launch {
            recordingsRepo.getRecordings().collect { recording ->
                _recordings.value = recording
            }
        }
    }

    fun startRecording() {
        recordingsRepo.startRecording()
    }

    fun getRecordingsTiedToNoteById(id: String): Flow<List<Recording>> {
        return recordingsRepo.getRecordingsTiedToNoteById(id = id).map { it.reversed() }
    }

    fun stopRecording(noteId: String?) {
        viewModelScope.launch {
            recordingsRepo.stopRecording(
                noteId = noteId,
            )
        }

        Log.d(TAG, "NoteId on Recording: $noteId")
    }

    suspend fun deleteRecordingFile() {
    }

//    fun updateFileNameRecording(recording: RecordingEntity) {
//        viewModelScope.launch {
//            recordingsRepo.deleteRecording(recording)
//        }
//    }

}
