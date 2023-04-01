package com.larsson.voicenote_android.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun TopAppBarCustom(
    onTextChangeTitle: (String) -> Unit,
    value: String,
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.height(60.dp),
        title = {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    selectionColors = TextSelectionColors(handleColor = MaterialTheme.colorScheme.onBackground, backgroundColor = MaterialTheme.colorScheme.onBackground.copy(0.3f)),
                ),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                singleLine = true,
                value = value,
                onValueChange = onTextChangeTitle,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painterResource(com.larsson.voicenote_android.R.drawable.custom_back_btn),
                        "backArrow",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.size(36.dp),
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "more")
            }
        },
    )
}

@Preview("Top App Bar", showBackground = true)
@Preview("Top App Bar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Top App Bar (big font)", fontScale = 1.5f)
@Preview("Top App Bar (large screen)", device = Devices.PIXEL_C)
@Composable
fun TopAppBarPreview() {
    VoiceNote_androidTheme {
        Column() {
            TopAppBarCustom(onTextChangeTitle = { it }, value = "hej", onBackClick = {})
        }
    }
}
