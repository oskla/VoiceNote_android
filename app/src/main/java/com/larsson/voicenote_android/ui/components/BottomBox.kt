package com.larsson.voicenote_android.ui.components // ktlint-disable package-name

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun BottomBox(
    modifier: Modifier = Modifier,
    textLeft: String,
    iconLeft: ImageVector,
    leftIconOffset: Dp = 0.dp,
    textRight: String,
    iconRight: ImageVector,
    bgColorSelected: Color = MaterialTheme.colors.secondary,
    onClickLeft: (() -> Unit)? = null,
    onClickRight: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    // notesViewModel.visibilityModifier(homeScreen = false)
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .align(CenterVertically)
            ) {
                Icon(
                    imageVector = iconLeft,
                    contentDescription = textLeft,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .offset(leftIconOffset)
                )
            }
            Text(
                text = textLeft,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .align(CenterVertically)
            ) {
                Icon(
                    imageVector = iconRight,
                    contentDescription = textRight,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)

                )
            }
            Text(
                text = textRight,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp
            )
        }
    }
}

private const val componentName = "Bottom Box"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        Column() {
            BottomBox(
                textLeft = "New note",
                textRight = "New recording",
                iconLeft = Icons.Filled.Add,
                iconRight = Icons.Filled.RadioButtonChecked,
                onClickLeft = {},
                onClickRight = {}
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            BottomBox(
                textLeft = "Recordings",
                textRight = "Record",
                iconLeft = Icons.Filled.List,
                iconRight = Icons.Filled.RadioButtonChecked,
                onClickLeft = {},
                onClickRight = {}
            )
        }
    }
}
