package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteItem(
    title: String,
    txtContent: String,
    id: String,
    onClick: () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.wrapContentSize(),
        elevation = 0.dp,
        shape = RectangleShape,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.W700
            )
            Text(
                text = txtContent,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.Thin,
                style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                fontSize = 14.sp,
                maxLines = 2
            )
        }
    }
}

private const val componentName = "Note Item"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        Column() {
            NoteItem(title = "Textidé 34", txtContent = "Det var en gång en katt som hette hund som drömde att han var en kanin", id = "1", onClick = {})
            Spacer(modifier = Modifier.height(1.dp))
            NoteItem(title = "Textidé 421", txtContent = "Det var en gång en katt som hette hund som drömde att han var en kanin", id = "1", onClick = {})
        }
    }
}
