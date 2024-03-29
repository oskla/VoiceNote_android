package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.ui.NotesList

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant,
    notes: MutableState<List<NoteEntity>>,
    recordings: List<RecordingEntity>,
    navController: NavController,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    onClickContainer: () -> Unit,
    playerState: PlayerState,
    currentPosition: Int,
    seekTo: (Float) -> Unit,
) {
    when (listVariant) {
        ListVariant.NOTES -> {
            NotesList(
                notes = notes,
                navController = navController,
                recordings = recordings,
            )
        }
        ListVariant.RECORDINGS -> {
            RecordingsList(
                recordings = recordings,
                onClickPlay = onClickPlay,
                onClickPause = onClickPause,
                playerState = playerState,
                onClickContainer = onClickContainer,
                currentPosition = currentPosition,
                seekTo = { position ->
                    seekTo(position)
                },
                isMenu = false,
            )
        }
    }
}
