package com.larsson.voicenote_android.features.audiorecorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.larsson.voicenote_android.data.repository.RecordingsRepository
import kotlinx.coroutines.launch

class RecordingBottomSheetViewModel(
    private val recordingsRepo: RecordingsRepository
) : ViewModel() {

    val TAG = "RecordingViewModel"

    fun startRecording() {
        recordingsRepo.startRecording()
    }

    fun stopRecording(noteId: String?) {
        viewModelScope.launch {
            recordingsRepo.stopRecording(
                noteId = noteId,
            )
        }

        Log.d(TAG, "NoteId on Recording: $noteId")
    }

}