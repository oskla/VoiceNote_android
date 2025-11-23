package com.larsson.voicenote_android.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.larsson.voicenote_android.clicklisteners.UiAudioPlayerClickListener
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.navigation.HomeNavigation
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.NotesList
import com.larsson.voicenote_android.ui.components.RecordingBottomSheet
import com.larsson.voicenote_android.ui.components.RecordingsList
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
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    onNavigateToNote: (String) -> Unit,
) {
    val recordingsState = recordingViewModel.recordings.collectAsState()
    val notesState = notesViewModel.notesStateFlow.collectAsState()
    val isPlaying = audioPlayerViewModel.isPlaying.collectAsState()
    val currentPosition = audioPlayerViewModel.currentPosition.collectAsState()

    HomeScreenContent(
        notesState = notesState,
        notesViewModel = notesViewModel,
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingsState = recordingsState,
        audioPlayerViewModel = audioPlayerViewModel,
        isPlaying = isPlaying,
        currentPosition = currentPosition,
        onNavigateToNote = onNavigateToNote,
        uiAudioPlayerClickListener = object : UiAudioPlayerClickListener {
            override fun onClickPlay(recording: Recording) {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Play(recording))
            }

            override fun onClickPause() {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Pause)
            }

            override fun onSeekTo(position: Float) {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.SeekTo(position))

            }

            override fun onSeekingFinished() {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.OnSeekFinished)
            }

            override fun onToggleExpandContainer(recordingId: String) {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.ToggleExpanded(recordingId))
            }

            override fun onClickDelete(recordingId: String) {
                audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Delete(recordingId))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenContent(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    notesState: State<List<Note>>,
    recordingsState: State<List<Recording>>,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    audioPlayerViewModel: AudioPlayerViewModel,
    isPlaying: State<Boolean>,
    currentPosition: State<Long>,
    onNavigateToNote: (String) -> Unit,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
) {
    val TAG = "HOME SCREEN"

    RecordingBottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
    )

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) {

        val backStack = rememberNavBackStack(HomeNavigation.NotesList)
        TopToggleBar(
            onNavigateToNotesList = {
                backStack.add(HomeNavigation.NotesList)
                backStack.remove(HomeNavigation.RecordingsList)
            },
            onNavigateToRecordingsList = {
                backStack.add(HomeNavigation.RecordingsList)
                backStack.remove(HomeNavigation.NotesList)
            }
        )
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.weight(1f),
            entryProvider = entryProvider {
                entry<HomeNavigation.NotesList> {
                    NotesList(
                        notes = notesState,
                        recordings = recordingsState.value,
                        onNavigateToNote = onNavigateToNote
                    )
                }
                entry<HomeNavigation.RecordingsList> {
                    RecordingsList(
                        recordings = recordingsState.value,
                        isPlaying = isPlaying,
                        currentPosition = currentPosition,
                        isMenu = false,
                        expandedContainerId = audioPlayerViewModel.expandedRecordingId.collectAsState(),
                        uiAudioPlayerClickListener = uiAudioPlayerClickListener,
                    )
                }
            }
        )

        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {
                openBottomSheet.value = true
            },
            onClickLeft = {
                notesViewModel.addNoteToRoom("", "").also { newNote ->
                    onNavigateToNote(newNote.id)
                }
            },
        )
    }
}
