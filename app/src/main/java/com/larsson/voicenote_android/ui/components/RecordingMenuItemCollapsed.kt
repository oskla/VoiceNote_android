package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNoteTheme

@Composable
fun RecordingMenuItemCollapsed(
    id: String,
    title: String,
    date: String,
    duration: String,
    isFirstItem: Boolean,
    onClickExpandContainer: (recordingId: String) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp,
    )

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .clickable() {
                onClickExpandContainer(id)
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(),
        shape = if (isFirstItem) roundedCornerShape else RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.W700,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Thin,
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                    fontSize = 14.sp,
                    maxLines = 2,
                )
                Text(
                    text = duration,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Thin,
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                    fontSize = 14.sp,
                    maxLines = 2,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecordingItemCollapsed() {
    VoiceNoteTheme() {
        RecordingMenuItemCollapsed(
            title = "A lil recording", date = "2025-04-03", duration = "00:50", isFirstItem = false,
            id = "",
            onClickExpandContainer = {}
        )
    }
}