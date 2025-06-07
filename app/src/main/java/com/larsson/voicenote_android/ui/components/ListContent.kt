package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.repository.Note
import com.larsson.voicenote_android.data.repository.Recording

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant,
    notes: State<List<Note>>,
    recordings: List<Recording>,
    navController: NavController,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    onClickContainer: () -> Unit,
    playerState: State<PlayerState>,
    currentPosition: State<Int>,
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
