package com.larsson.voicenote_android.features

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.helpers.dateFormatter
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.MoreCircleMenu
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.components.RecordingMenu
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    noteId: String,
    audioPlayerViewModel: AudioPlayerViewModel,
) {
    val TAG = "EDIT NOTE SCREEN"

    val recordingState by recordingViewModel.recordings.collectAsState()
    val playerState by audioPlayerViewModel.playerState.collectAsState()
    val currentPosition by audioPlayerViewModel.currentPosition.collectAsState()
    val recordingsTiedToNoteState =
        recordingViewModel.getRecordingsTiedToNoteById(noteId).collectAsState(emptyList())
    val selectedNote = viewModel.currentNoteStateFlow.collectAsState()

    fun uiEventSetToIdle() = audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.SetToIdle)
    fun uiEventPause() = audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.Pause)
    fun uiEventPlay(recordingId: String) =
        audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.Play(recordingId))

    fun uiEventSeekTo(position: Int) =
        audioPlayerViewModel.handleUIEvents(AudioPlayerEvent.SeekTo(position))

    when (playerState) {
        PlayerState.Completed -> Log.d("Home", "State: Completed")
        is PlayerState.Error -> Log.d("Home", "State: Error") // TODO show error dialog
        PlayerState.Idle -> Log.d("Home", "State: Idle")
        PlayerState.Paused -> Log.d("Home", "State: Paused")
        is PlayerState.Playing -> Log.d("Home", "State: Playing")
        PlayerState.End -> Log.d("Home", "State: End")
        PlayerState.Initialized -> Log.d("Home", "State: Initialized")
        PlayerState.Prepared -> Log.d("Home", "State: Prepared")
        PlayerState.Stopped -> Log.d("Home", "State: Stopped")
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getNoteFromRoomById(noteId)
    }

    BottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingNoteId = noteId,
    )

    selectedNote.value?.let { noteEntity ->
        EditNoteContent(
            note = noteEntity,
            viewModel = viewModel,
            navController = navController,
            noteId = noteId,
            recordings = recordingsTiedToNoteState,
            openBottomSheet = openBottomSheet,
            onClickPlay = { recordingId -> uiEventPlay(recordingId) },
            onClickPause = { uiEventPause() },
            playerState = playerState,
            currentPosition = currentPosition,
            onClickContainer = { uiEventSetToIdle() },
            seekTo = { position ->
                uiEventSeekTo(position.toInt())
            },
        )
    }
}

@Composable
fun EditNoteContent(
    note: NoteEntity,
    viewModel: NotesViewModel,
    navController: NavController,
    noteId: String?,
    recordings: State<List<Recording>>,
    openBottomSheet: MutableState<Boolean>,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    playerState: PlayerState,
    currentPosition: Int,
    seekTo: (Float) -> Unit,
    onClickContainer: () -> Unit,

    ) {
    val TAG = "EDIT NOTE CONTENT"
    val title by remember { mutableStateOf(note.noteTitle) }
    val textContent by remember { mutableStateOf(note.noteTxtContent) }

    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }
    var showRecordingMenu by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val blur = 3.dp

    // TODO remove constraintLayout
    ConstraintLayout(
        modifier = Modifier,
    ) {
        val (noteView, bottomBox, menu, background, moreMenu) = createRefs()

        NoteView(
            modifier = Modifier
                .constrainAs(noteView) {}
                .blur(if (showMoreMenu) blur else 0.dp),
            onBackClick = {
                // If note has not changed, don't update the note
                if (title != textFieldValueTitle || textContent != textFieldValueContent) {
                    viewModel.updateNoteRoom(
                        title = textFieldValueTitle,
                        txtContent = textFieldValueContent,
                        id = noteId ?: "000",
                    )
                }

                navController.popBackStack()
            },
            textFieldValueContent = textFieldValueContent,
            textFieldValueTitle = textFieldValueTitle,
            onTextChangeTitle = { textFieldValueTitle = it },
            onTextChangeContent = { textFieldValueContent = it },
            date = dateFormatter(note.date),
            onMoreClick = { showMoreMenu = true },
        )

        if (showMoreMenu) {
            MoreCircleMenu(
                onClickDelete = {
                    coroutineScope.launch {
                        if (noteId != null) {
                            viewModel.deleteNoteByIdRoom(noteId)
                            navController.popBackStack()
                        }
                    }
                },
                onClickShare = {},
                modifier = Modifier
                    .zIndex(4f)
                    .constrainAs(moreMenu) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                onClickDismiss = { showMoreMenu = false },
            )
        }

        AnimatedVisibility(
            visible = showRecordingMenu,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBox.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.3f))
                    .zIndex(1F)
                    .clickable {
                        showRecordingMenu = false
                    },
            ) {}
        }
        AnimatedVisibility(
            visible = showRecordingMenu,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
                .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f)
                .constrainAs(menu) {
                    bottom.linkTo(bottomBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBox.top)
                }
                .zIndex(2f),
        ) {
            RecordingMenu(
                recordings = recordings.value,
                onClickPlay = onClickPlay,
                onClickPause = onClickPause,
                playerState = playerState,
                currentPosition = currentPosition,
                onClickContainer = onClickContainer,
                seekTo = seekTo,
            )
        }

        BottomBox(
            modifier = Modifier
                .zIndex(3f)
                .blur(if (showMoreMenu) blur else 0.dp)
                .constrainAs(bottomBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            variant = Variant.RECORDINGS_RECORD,
            onClickLeft = { showRecordingMenu = !showRecordingMenu },
            onClickRight = {
                showRecordingMenu = false
                openBottomSheet.value = true
            },
        )
    }
}
