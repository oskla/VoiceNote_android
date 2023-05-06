package com.larsson.voicenote_android.features // ktlint-disable package-name

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.navigation.Screen
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.ListContent
import com.larsson.voicenote_android.ui.components.ListVariant
import com.larsson.voicenote_android.ui.components.TopToggleBar
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    audioPlayerViewModel: AudioPlayerViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
) {
    val recordingsState by recordingViewModel.recordings.collectAsState()
    val playerState by audioPlayerViewModel.playerState.collectAsState()
    val currentPosition by audioPlayerViewModel.currentPosition.collectAsState()
    val audioItems by audioPlayerViewModel.audioItems.collectAsState()

    val notesState = remember { mutableStateOf(emptyList<NoteEntity>()) }
    val recordings = remember { mutableStateOf(emptyList<RecordingEntity>()) }
    var isDataFetched by remember { mutableStateOf(false) }

    when (playerState) {
        AudioPlayerViewModel.PlayerState.Completed -> Log.d("Home", "State: Completed")
        is AudioPlayerViewModel.PlayerState.Error -> Log.d("Home", "State: Error")
        AudioPlayerViewModel.PlayerState.Idle -> Log.d("Home", "State: Idle")
        AudioPlayerViewModel.PlayerState.Paused -> Log.d("Home", "State: Paused")
        AudioPlayerViewModel.PlayerState.Playing -> Log.d("Home", "State: Playing")
    }

    Log.d("HOME", formatDuration(currentPosition))

    LaunchedEffect(key1 = recordingsState) {
        val notes = notesViewModel.getAllNotesFromRoom()
        val fetchedRecordings = recordingViewModel.getAllRecordingsRoom()
        notesState.value = notes
        recordings.value = fetchedRecordings
        isDataFetched = true
    }

    if (!isDataFetched) {
        // Show loader
        return
    }

    HomeScreenContent(
        notesState = notesState,
        navController = navController,
        notesViewModel = notesViewModel,
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingsState = recordings,
        audioPlayerViewModel = audioPlayerViewModel,
        playerState = playerState,
        audioItems = audioItems,
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    notesState: MutableState<List<NoteEntity>>,
    recordingsState: MutableState<List<RecordingEntity>>,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    audioPlayerViewModel: AudioPlayerViewModel,
    playerState: AudioPlayerViewModel.PlayerState,
    audioItems: List<AudioPlayerViewModel.AudioItem>,
) {
    val TAG = "HOME SCREEN"

    val notesListVisible = notesViewModel.notesListVisible

    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, recordingViewModel = recordingViewModel)

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        TopToggleBar(modifier = Modifier, viewModel = notesViewModel)

        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            ListContent(
                listVariant = if (notesListVisible) ListVariant.NOTES else ListVariant.RECORDINGS,
                notes = notesState,
                navController = navController,
                recordings = recordingsState,
                onClickPlay = {
                    Log.d(TAG, it)
                    audioPlayerViewModel.play(it)
                },
                onClickPause = {
                    audioPlayerViewModel.pause()
                },
                playerState = playerState,
                audioItems = audioItems,
            )
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {
                openBottomSheet.value = true
            },
            onClickLeft = {
                notesViewModel.addNoteToRoom("Title", "").also { newNote ->
                    navController.navigate("${Screen.NewNote.route}/${newNote.noteId}")
                }
            },
        )
    }
}

private fun formatDuration(duration: Int): String {
    val minutes = duration / 1000 / 60
    val seconds = duration / 1000 % 60
    return "$minutes:${String.format("%02d", seconds)}"
}
