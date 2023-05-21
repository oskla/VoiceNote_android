package com.larsson.voicenote_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily

@Composable
fun RecordingMenuItemBase(
    title: String,
    date: String,
    duration: String,
    isFirstItem: Boolean,
) {
    val roundedCornerShape = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp,
    )

    Card(
        modifier = Modifier.wrapContentHeight(),
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 0.dp,
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
