package com.larsson.voicenote_android.features // ktlint-disable package-name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.repository.Note
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
    val isPlaying = audioPlayerViewModel.isPlaying.collectAsState()
    val currentPosition = audioPlayerViewModel.currentPosition.collectAsState()

    HomeScreenContent(
        notesState = notesState,
        navController = navController,
        notesViewModel = notesViewModel,
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingsState = recordingsState,
        audioPlayerViewModel = audioPlayerViewModel,
        isPlaying = isPlaying,
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
    notesState: State<List<Note>>,
    recordingsState: State<List<Recording>>,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    audioPlayerViewModel: AudioPlayerViewModel,
    isPlaying: State<Boolean>,
    currentPosition: State<Long>,
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
        TopToggleBar(viewModel = notesViewModel)

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
                onToggleExpandContainer = { shouldExpand, id ->
                    audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.ToggleExpanded(shouldExpand = shouldExpand, recordingId = id))
                },
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                seekTo = seekTo,
                expandedContainerState = audioPlayerViewModel.isExpanded.collectAsState()
            )
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {
                openBottomSheet.value = true
            },
            onClickLeft = {
                notesViewModel.addNoteToRoom("", "").also { newNote ->
                    navController.navigate("${Screen.EditNote.route}/${newNote.id}")
                }
            },
        )
    }
}

