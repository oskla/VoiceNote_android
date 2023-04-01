package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.helpers.dateFormatter
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.MoreCircleMenu
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.components.RecordingMenu
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import kotlinx.coroutines.launch

// TODO on swipe back, do something else, now it's just empty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    noteId: String,
) {
    val TAG = "EDIT NOTE SCREEN"

    val recordingState by recordingViewModel.recordings.collectAsState()
    var selectedNote by remember { mutableStateOf<NoteEntity?>(null) }
    val recordings = remember { mutableStateOf(emptyList<RecordingEntity>()) }
    var isDataFetched by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = recordingState) {
        val note = viewModel.getNoteFromRoomById(id = noteId)
        recordings.value = recordingViewModel.getRecordingsTiedToNoteById(noteId)
        selectedNote = note
        isDataFetched = true
    }

    if (!isDataFetched) {
        // Show loader
        return
    }
    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, recordingViewModel = recordingViewModel, recordingNoteId = noteId)
    selectedNote?.let {
        EditNoteContent(
            selectedNote = it,
            viewModel = viewModel,
            navController = navController,
            noteId = noteId,
            recordings = recordings,
            openBottomSheet = openBottomSheet,
        )
    }
}

@Composable
fun EditNoteContent(
    selectedNote: NoteEntity,
    viewModel: NotesViewModel,
    navController: NavController,
    noteId: String?,
    isNewNote: Boolean? = false,
    recordings: MutableState<List<RecordingEntity>>,
    openBottomSheet: MutableState<Boolean>,

) {
    val TAG = "EDIT NOTE CONTENT"
    val title by remember { mutableStateOf(selectedNote.noteTitle) }
    val textContent by remember { mutableStateOf(selectedNote.noteTxtContent) }

    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }
    var showRecordingMenu by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()

    ConstraintLayout() {
        val (noteView, bottomBox, menu, background, moreMenu) = createRefs()

        NoteView(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .constrainAs(noteView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBox.top)
                },
            onBackClick = {
                // If note has not been change, don't update the note
                if (title != textFieldValueTitle || textContent != textFieldValueContent) {
                    viewModel.updateNoteRoom(title = textFieldValueTitle, txtContent = textFieldValueContent, id = noteId ?: "000")
                }

                navController.popBackStack()
            },
            textFieldValueContent = textFieldValueContent,
            textFieldValueTitle = textFieldValueTitle,
            onTextChangeTitle = { textFieldValueTitle = it },
            onTextChangeContent = { textFieldValueContent = it },
            date = dateFormatter(selectedNote.date),
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

        if (showRecordingMenu) {
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(0.3f))
                    .zIndex(1F)
                    .clickable {
                        showRecordingMenu = false
                    }
                    .constrainAs(background) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomBox.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
            ) {}
        }
        AnimatedVisibility(
            visible = showRecordingMenu,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
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
                noteId = selectedNote.noteId,
                recordings = recordings.value,
            )
        }

        BottomBox(
            modifier = Modifier
                .zIndex(3f)
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
