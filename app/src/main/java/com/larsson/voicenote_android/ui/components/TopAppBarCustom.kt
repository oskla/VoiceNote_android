package com.larsson.voicenote_android.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun TopAppBarCustom(
    onTextChangeTitle: (String) -> Unit,
    value: String,
    onBackClick: () -> Unit = {}

) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.height(60.dp),
        title = {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = value,
                onValueChange = onTextChangeTitle
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painterResource(com.larsson.voicenote_android.R.drawable.custom_back_btn),
                        "backArrow",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}

/* TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.height(60.dp),
            title = {
                TextField(
                textStyle = MaterialTheme.typography.caption,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.primary,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                    ),
                value = textFieldValue2,
                onValueChange = {
                    textFieldValue2 = it
                })
            },
            navigationIcon = {
                IconButton(onClick = {
                  viewModel.saveNote(textFieldValue2, textFieldValue, id)
                    navController.popBackStack()
                }) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            "arrow_back_ios_new",
                            tint = MaterialTheme.colors.primary
                        )
                        Text(text = "Back")

                    }
                }
            }
        )*/

@Preview("Top App Bar", showBackground = true)
@Preview("Top App Bar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Top App Bar (big font)", fontScale = 1.5f)
@Preview("Top App Bar (large screen)", device = Devices.PIXEL_C)
@Composable
fun TopAppBarPreview() {
    VoiceNote_androidTheme {
        TopAppBarCustom(onTextChangeTitle = {}, value = "hej")
    }
}