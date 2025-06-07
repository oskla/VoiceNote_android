package com.larsson.voicenote_android.features // ktlint-disable package-name

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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.repository.Recording
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
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent

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
    val recordingsState = recordingViewModel.recordings.collectAsState()
    val notesState = notesViewModel.notesStateFlow.collectAsState()
    val playerState by audioPlayerViewModel.playerState.collectAsState()
    val currentPosition by audioPlayerViewModel.currentPosition.collectAsState()

    var isDataFetched by remember { mutableStateOf(false) }

    when (playerState) {
        PlayerState.Completed -> Log.d("Home", "State: Completed")
        is PlayerState.Error -> Log.d("Home", "State: Error") // TODO add error screen / dialog
        PlayerState.Idle -> Log.d("Home", "State: Idle")
        PlayerState.Paused -> Log.d("Home", "State: Paused")
        is PlayerState.Playing -> Log.d("Home", "State: Playing")
        PlayerState.End -> Log.d("Home", "State: End")
        PlayerState.Initialized -> Log.d("Home", "State: Initialized")
        PlayerState.Prepared -> Log.d("Home", "State: Prepared")
        PlayerState.Stopped -> Log.d("Home", "State: Stopped")
    }

    LaunchedEffect(key1 = recordingsState.value) {
        isDataFetched = true
    }

    if (!isDataFetched) {
        // TODO Show loader
        return
    }

    HomeScreenContent(
        notesState = notesState,
        navController = navController,
        notesViewModel = notesViewModel,
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingsState = recordingsState,
        audioPlayerViewModel = audioPlayerViewModel,
        playerState = playerState,
        currentPosition = currentPosition,
        seekTo = { position ->
            audioPlayerViewModel.handleUIEvents(
                AudioPlayerEvent.SeekTo(position.toInt()),
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    notesState: State<List<NoteEntity>>,
    recordingsState: State<List<Recording>>,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    audioPlayerViewModel: AudioPlayerViewModel,
    playerState: PlayerState,
    currentPosition: Int,
    seekTo: (Float) -> Unit,
) {
    val TAG = "HOME SCREEN"

    val notesListVisible = notesViewModel.notesListVisible

    BottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel
    )

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
                recordings = recordingsState.value,
                onClickPlay = { recordingId ->
                    audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Play(recordingId))
                },
                onClickPause = {
                    audioPlayerViewModel.pause()
                },
                onClickContainer = { audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.SetToIdle) },
                playerState = playerState,
                currentPosition = currentPosition,
                seekTo = seekTo,
            )
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {
                openBottomSheet.value = true
            },
            onClickLeft = {
                notesViewModel.addNoteToRoom("Title", "").also { newNote ->
                    navController.navigate("${Screen.EditNote.route}/${newNote.noteId}")
                }
            },
        )
    }
}

