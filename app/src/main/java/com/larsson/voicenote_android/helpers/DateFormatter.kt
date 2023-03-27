package com.larsson.voicenote_android.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateFormatter(dateString: String) {

    /** The different lengths of [.SSSSSS] is for providing optional lengths to the ending integers **/
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][.SSSSS][.SSSS][.SSS]")

    val dateTime = LocalDateTime.parse(dateString, formatter)

    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formattedDateTime = dateTime.format(formatter2)

    fun getFormattedTime(): String {
        return formattedDateTime
    }

    fun getNowTime(): String {
        val nowDateTime = LocalDateTime.parse(LocalDateTime.now().toString(), formatter)
        return nowDateTime.format(formatter2)
    }
}
