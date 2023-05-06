package com.larsson.voicenote_android.features

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.ui.EditNoteContent
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteScreen(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    noteId: String,
    audioPlayerViewModel: AudioPlayerViewModel,
) {
    val recordingState by recordingViewModel.recordings.collectAsState()
    val playerState by audioPlayerViewModel.playerState.collectAsState()

    var selectedNote by remember { mutableStateOf<NoteEntity?>(null) }
    val recordings = remember { mutableStateOf(emptyList<RecordingEntity>()) }
    var isDataFetched by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = recordingState) {
        notesViewModel.getNoteFromRoomById(noteId).also { note ->
            selectedNote = note
            recordings.value = recordingViewModel.getRecordingsTiedToNoteById(note.noteId)
        }

        isDataFetched = true
    }

    if (!isDataFetched) {
        // Show loader
        return
    }
    BottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingNoteId = selectedNote?.noteId,
    )

    selectedNote?.let { noteEntity ->
        EditNoteContent(
            selectedNote = noteEntity,
            viewModel = notesViewModel,
            navController = navController,
            noteId = noteId,
            recordings = recordings,
            openBottomSheet = openBottomSheet,
            isNewNote = true,
            onClickPlay = { audioPlayerViewModel.play(it) },
            onClickPause = { /* TODO */ },
            playerState = playerState,
        )
    }
}
