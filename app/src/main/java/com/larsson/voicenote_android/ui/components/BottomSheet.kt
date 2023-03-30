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
import androidx.compose.foundation.layout.padding
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
import com.larsson.voicenote_android.data.entity.Recording
import com.larsson.voicenote_android.ui.lottie.LottieLRecording
import com.larsson.voicenote_android.viewmodels.RecordingViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    modifier: Modifier = Modifier,
    recordingViewModel: RecordingViewModel,
) {
    val TAG = "Bottom Sheet"
    var audioFile: File? = null

    LaunchedEffect(key1 = true) {
       /* recordingViewModel.startRecording()
        Log.d(TAG, "recording started")*/
    }

    if (openBottomSheet.value) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                recordingViewModel.stopRecording()
                Log.d(TAG, "recording stopped")

                openBottomSheet.value = false
                val recording = Recording( // TODO replace with actual Room-data
                    recordingDate = "2023",
                    recordingLink = "www.link.se",
                    recordingTitle = "The recording",
                )
               // Log.d("Recording", recording.toString())
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
                    LottieLRecording(
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
