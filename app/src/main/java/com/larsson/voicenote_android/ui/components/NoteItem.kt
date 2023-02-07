package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily

@Composable
fun NoteItem(
    title: String,
    txtContent: String,
    id: String,
    onClick: () -> Unit
) {
    Card(
        backgroundColor = Color.Transparent,
        modifier = Modifier.wrapContentSize(),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(onClick = {})
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.W700
            )
            Text(
                text = txtContent,
                color = MaterialTheme.colors.primary,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.Thin,
                style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                fontSize = 14.sp,
                maxLines = 2
            )
        }
    }
}
