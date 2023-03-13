package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun TopToggleBar() {
    var selectedLeft by remember { mutableStateOf(true) }
    var selectedRight by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp)
            .border(BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline), shape = shapes.medium)
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            ToggleBox(
                text = "Notes",
                selected = selectedLeft,
                onClick = {
                    selectedLeft = !selectedLeft
                    selectedRight = !selectedRight
                }
            )
        }
        Column(
            Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            ToggleBox(
                text = "Recordings",
                onClick = {
                    selectedRight = !selectedRight
                    selectedLeft = !selectedLeft
                },
                selected = selectedRight
            )
        }
    }
}

@Composable
fun ToggleBox(
    text: String,
    height: Dp = 24.dp,
    selected: Boolean,
    onClick: () -> Unit
) {
    var checked by remember { mutableStateOf(selected) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background, shapes.medium)
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text, color = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TogglePreview() {
    VoiceNote_androidTheme {
        TopToggleBar()
    }
}
