package com.larsson.voicenote_android.features // ktlint-disable package-name

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.ListContent
import com.larsson.voicenote_android.ui.components.ListVariant
import com.larsson.voicenote_android.ui.components.TopToggleBar
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {
    val getAllNotes = notesViewModel.getAllNotes()
    val newNoteId = getUUID()

    val newNoteVisible = notesViewModel.newNoteVisible
    val notesListVisible = notesViewModel.notesListVisible

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        TopToggleBar(modifier = Modifier, viewModel = notesViewModel)

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            if (newNoteVisible) {
                NewNoteScreen(notesViewModel, newNoteId)
            }
            ListContent(listVariant = if (notesListVisible) ListVariant.NOTES else ListVariant.RECORDINGS)
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {},
            onClickLeft = {}
        )
    }
}

private const val componentName = "Home Screen"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        HomeScreen(notesViewModel = NotesViewModel())
    }
}
