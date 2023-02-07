package com.larsson.voicenote_android.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme



@Composable
fun TopToggleBar() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(horizontal = 24.dp)
            //.background(Color.Red, shape = shapes.medium)
            .border(BorderStroke(0.8.dp, MaterialTheme.colors.onPrimary), shape = shapes.medium)
    )

    {

        Column(Modifier.weight(1f).padding(4.dp)) {
            ToggleBox(text = "Notes", bgColor = MaterialTheme.colors.onPrimary, txtColor = MaterialTheme.colors.background)
        }
        Column(Modifier.weight(1f).padding(4.dp)) {
            ToggleBox(text = "Recordings", bgColor = MaterialTheme.colors.background, txtColor = MaterialTheme.colors.onPrimary)
        }
    }
}

@Composable
fun ToggleBox(
    text: String,
    bgColor: androidx.compose.ui.graphics.Color,
    txtColor: androidx.compose.ui.graphics.Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(bgColor, shape = shapes.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text, color = txtColor)
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TogglePreview() {
    VoiceNote_androidTheme {
        TopToggleBar()
    }
}