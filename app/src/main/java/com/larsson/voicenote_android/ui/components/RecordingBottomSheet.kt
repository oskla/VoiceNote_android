package com.larsson.voicenote_android.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.larsson.voicenote_android.ui.lottie.LottieRecording
import com.larsson.voicenote_android.features.audiorecorder.RecordingBottomSheetViewModel
import org.koin.androidx.compose.koinViewModel

// TODO ask for permission again if you say no

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingBottomSheet(
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    modifier: Modifier = Modifier,
    recordingBottomSheetViewModel: RecordingBottomSheetViewModel = koinViewModel(),
    recordingNoteId: String? = null
) {
    val TAG = "Recording bottom Sheet"
    if (openBottomSheet.value) {
        LaunchedEffect(key1 = true) {
            recordingBottomSheetViewModel.startRecording()
            Log.d(TAG, "recording started")
        }

        ModalBottomSheet(
            modifier = Modifier
                .navigationBarsPadding()
                .systemBarsPadding(),
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                recordingBottomSheetViewModel.stopRecording(noteId = recordingNoteId)
                openBottomSheet.value = false
            },
            sheetState = bottomSheetState,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 40.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    LottieRecording(
                        file = if (isSystemInDarkTheme()) "sound-wave-dark-mode.json" else "sound-wave.json",
                        modifier = modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        iterations = LottieConstants.IterateForever,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Recording...")
                }
            }
        }
    }
}
