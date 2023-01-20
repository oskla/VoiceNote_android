package com.larsson.voicenote_android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@SuppressLint("ResourceType")
@Composable
fun TopAppBar(
    onTextChangeTitle: (String) -> Unit,
    value: String,
    onBackClick:  () -> Unit = {}

) {
    TopAppBar(
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
                value = value,
                onValueChange = onTextChangeTitle)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painterResource(com.larsson.voicenote_android.R.drawable.custom_back_btn),
                        "backArrow",
                        tint = MaterialTheme.colors.primary
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
