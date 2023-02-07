package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun BottomBox(
    newNoteId: String,
    notesViewModel: NotesViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    notesViewModel.visibilityModifier(homeScreen = false)
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    "add",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
            }
            Text(text = "New note", color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(end = 6.dp)) {
                Icon(
                    imageVector = Icons.Filled.RadioButtonChecked,
                    "radio_button_checked",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            Text(
                text = "New recording",
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )
        }
    }
}