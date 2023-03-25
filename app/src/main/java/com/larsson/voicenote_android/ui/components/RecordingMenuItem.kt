package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenuItemBase(
    title: String,
    date: String,
    id: String,
    duration: String,
) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.wrapContentSize(),
        elevation = 0.dp,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable(onClick = {})
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.W700
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Thin,
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Text(
                    text = duration,
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
}

@Composable
fun RecordingMenuItemPlayer(
    title: String,
    date: String,
    id: String,
    duration: String,
    onClick: (() -> Unit)? = null,
    progress: Float?,
// TODO take in state?
) {
        Card(
            backgroundColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.wrapContentSize(),
            elevation = 0.dp,
            shape = RectangleShape,

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {})
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(

                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontFamily = SpaceGroteskFontFamily,
                        fontWeight = FontWeight.W700
                    )
                    Text(
                        text = duration,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = SpaceGroteskFontFamily,
                        fontWeight = FontWeight.Thin,
                        style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                }


                LinearProgressBar(
                    progress = progress ?: 0f,
                    color = MaterialTheme.colorScheme.onBackground,
                    onProgressChanged = {

                    }
                )
                AudioPlayerRow(date = "2013-03-21", onClickDelete = { TODO() }, onClickPlay = { TODO() })

            }
        }
    }

@Composable
fun AudioPlayerRow(
    date: String,
    onClickPlay: () -> Unit,
    onClickDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "play",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .clickable { onClickPlay.invoke() }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                text = date,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.Thin,
                style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                fontSize = 14.sp,
                maxLines = 2
            )


                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onClickDelete.invoke() }
                        .height(30.dp)
                        .width(30.dp)

                )
            }
        }
    }
}


@Composable
fun RecordingMenuItem(
    title: String,
    date: String,
    id: String,
    duration: String,
    onClick: (() -> Unit)? = null,
    isShowingPlayer: Boolean,
    progress: Float?,
// TODO take in state?
) {
    Column() {


        Card(
            backgroundColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.wrapContentSize(),
            elevation = 0.dp,
            shape = RectangleShape
        ) {
            RecordingMenuItemBase(
                title = title,
                date = date,
                id = id,
                duration = duration
            )
        }
            if (isShowingPlayer) {
                LinearProgressBar(
                    progress = progress ?: 0f,
                    color = MaterialTheme.colorScheme.onBackground,
                    onProgressChanged = {

                    }
                )
            }
    }
}


private const val componentName = "Recording Menu Item"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuItem() {
    VoiceNote_androidTheme {
        Column {
            RecordingMenuItemPlayer(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                progress = 0.4F
            )
            Divider()
            RecordingMenuItem(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                isShowingPlayer = false,
                progress = null
            )
        }
    }
}
