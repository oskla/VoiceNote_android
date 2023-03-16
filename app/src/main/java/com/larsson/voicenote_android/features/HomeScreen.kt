package com.larsson.voicenote_android.features // ktlint-disable package-name

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.navigation.Screen
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.ListContent
import com.larsson.voicenote_android.ui.components.ListVariant
import com.larsson.voicenote_android.ui.components.TopToggleBar
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    scope: CoroutineScope
) {
    val allNotes = notesViewModel.notes
    val notesListVisible = notesViewModel.notesListVisible

    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, scope = scope)

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        TopToggleBar(modifier = Modifier, viewModel = notesViewModel)

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            ListContent(
                listVariant = if (notesListVisible) ListVariant.NOTES else ListVariant.RECORDINGS,
                notes = allNotes,
                navController = navController,
                notesViewModel = notesViewModel
            )
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = { openBottomSheet.value = true },
            onClickLeft = { navController.navigate(Screen.NewNote.route) }
        )
    }
}

private const val componentName = "Home Screen"

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        HomeScreen(
            notesViewModel = NotesViewModel(),
            navController = rememberNavController(),
            bottomSheetState = rememberModalBottomSheetState(),
            openBottomSheet = mutableStateOf(true),
            scope = rememberCoroutineScope()
        )
    }
}
