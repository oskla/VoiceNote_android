package com.larsson.voicenote_android.features

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.larsson.voicenote_android.clicklisteners.UiAudioPlayerClickListener
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.helpers.dateFormatter
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.MoreCircleMenu
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.components.RecordingBottomSheet
import com.larsson.voicenote_android.ui.components.RecordingMenu
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.AudioPlayerViewModel
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditNoteScreen(
    viewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    noteId: String,
    audioPlayerViewModel: AudioPlayerViewModel,
    onBackClick: () -> Unit,
) {
    val TAG = "EDIT NOTE SCREEN"

    val isPlaying = audioPlayerViewModel.isPlaying.collectAsState()
    val currentPosition = audioPlayerViewModel.currentPosition.collectAsState()
    val recordingsTiedToNoteState =
        recordingViewModel.getRecordingsTiedToNoteById(noteId).collectAsState(emptyList())
    val selectedNote = viewModel.currentNoteStateFlow.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getNoteFromRoomById(noteId)
    }

    RecordingBottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingNoteId = noteId,
    )

    selectedNote.value?.let { note ->
        EditNoteContent(
            note = note,
            viewModel = viewModel,
            noteId = noteId,
            recordings = recordingsTiedToNoteState,
            openBottomSheet = openBottomSheet,
            isPlaying = isPlaying,
            currentPosition = currentPosition,
            expandedContainerState = audioPlayerViewModel.expandedRecordingId.collectAsState(),
            onBackClick = onBackClick,
            uiAudioPlayerClickListener = object : UiAudioPlayerClickListener {
                override fun onClickPlay(recordingId: String) {
                    audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Play(recordingId))
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
}

@Composable
private fun EditNoteContent(
    note: Note,
    viewModel: NotesViewModel,
    noteId: String?,
    recordings: State<List<Recording>>,
    openBottomSheet: MutableState<Boolean>,
    isPlaying: State<Boolean>,
    currentPosition: State<Long>,
    expandedContainerState: State<String>,
    onBackClick: () -> Unit,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
) {
    val TAG = "EDIT NOTE CONTENT"
    val title by remember { mutableStateOf(note.title) }
    val textContent by remember { mutableStateOf(note.textContent) }

    // TODO can i move the textfield-stuff into NoteView So that selectedNote can be accessed
    //  here instead of in screen?
    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }
    var showRecordingMenu by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val blur = 3.dp

    // TODO remove constraintLayout
    ConstraintLayout(
        modifier = Modifier
            .navigationBarsPadding()
            .systemBarsPadding(),
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

                onBackClick()
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
                            onBackClick()
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
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                expandedContainerState = expandedContainerState,
                uiAudioPlayerClickListener = uiAudioPlayerClickListener
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
