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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.larsson.voicenote_android.features.editnotescreen.EditNoteViewModel
import com.larsson.voicenote_android.viewmodels.interfaces.AudioPlayerEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditNoteScreen(
    editNoteViewModel: EditNoteViewModel,
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
        editNoteViewModel.getRecordingsTiedToNoteById(noteId).collectAsState(emptyList())
    val selectedNote = editNoteViewModel.currentNoteStateFlow.collectAsState()

    LaunchedEffect(key1 = Unit) {
        editNoteViewModel.getNoteFromRoomById(noteId)
    }

    RecordingBottomSheet(
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingNoteId = noteId,
    )

    selectedNote.value?.let { note ->
        EditNoteContent(
            note = note,
            noteId = noteId,
            recordings = recordingsTiedToNoteState,
            openBottomSheet = openBottomSheet,
            isPlaying = isPlaying,
            currentPosition = currentPosition,
            expandedContainerState = audioPlayerViewModel.expandedRecordingId.collectAsState(),
            onBackClick = onBackClick,
            onDeleteNote = { id ->
                editNoteViewModel.deleteNote(id)
            },
            onUpdateNote = { title, textContent ->
                editNoteViewModel.updateNoteRoom(
                    title = title,
                    txtContent = textContent,
                    id = noteId,
                )
            },
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

                override fun onCollapsedContainerClicked(recordingId: String) {
                    audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.ExpandContainer(recordingId))
                }

                override fun onClickDelete(recordingId: String) {
                    audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.Delete(recordingId))
                }

                override fun onTitleValueChange(title: String, recordingId: String) {
                    audioPlayerViewModel.handleUIEvents(event = AudioPlayerEvent.OnTitleValueChange(title = title, recordingId = recordingId))
                }

            }

        )
    }
}

@Composable
private fun EditNoteContent(
    note: Note,
    noteId: String?,
    recordings: State<List<Recording>>,
    openBottomSheet: MutableState<Boolean>,
    isPlaying: State<Boolean>,
    currentPosition: State<Long>,
    expandedContainerState: State<String>,
    onBackClick: () -> Unit,
    onUpdateNote: (title: String, textContent: String) -> Unit,
    onDeleteNote: (id: String) -> Unit,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
) {
    val TAG = "EDIT NOTE CONTENT"
    val initialTitle by remember { mutableStateOf(note.title) }
    val initialTextContent by remember { mutableStateOf(note.textContent) }

    // TODO can i move the textfield-stuff into NoteView So that selectedNote can be accessed
    //  here instead of in screen?
    var textFieldValueContent by rememberSaveable { mutableStateOf(initialTextContent) }
    var textFieldValueTitle by rememberSaveable { mutableStateOf(initialTitle) }
    var showRecordingMenu by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }

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
                if (initialTitle != textFieldValueTitle || initialTextContent != textFieldValueContent) {
                    onUpdateNote(textFieldValueTitle, textFieldValueContent)
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
                    if (noteId != null) {
                        onDeleteNote(noteId)
                        onBackClick()
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
