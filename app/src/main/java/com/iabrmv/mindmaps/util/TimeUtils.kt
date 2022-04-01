package com.iabrmv.mindmaps.util

import android.text.format.DateUtils
import android.text.format.DateUtils.*
import com.iabrmv.mindmaps.util.TimeUtils.toDateTimeFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days


object TimeUtils {

    fun Long.toDateString(): String {
        val now = now()
        return when(now - this) {
            in 0 until MINUTE_IN_MILLIS -> "just now"
            in MINUTE_IN_MILLIS until 2 * MINUTE_IN_MILLIS -> "a minute ago"
            in 2 * MINUTE_IN_MILLIS until HOUR_IN_MILLIS ->
                "${(now - this) / MINUTE_IN_MILLIS } minutes ago"
            else -> if(isToday(now)) {
                    "today, ${toTimeFormat()}"
                } else {
                    if(isYesterday(now)) {
                        "yesterday, ${toTimeFormat()}"
                    } else {
                        toDateTimeFormat()
                    }
                }
        }
    }

    private fun isYesterday(timeInMillis: Long): Boolean =
        isToday(timeInMillis + DAY_IN_MILLIS)

    private fun Long.toTimeFormat() = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        .format(this)

    private fun Long.toDateTimeFormat() = SimpleDateFormat("dd MM yyyy, HH:mm", Locale.ENGLISH)
        .format(this)

    private fun now() = Calendar.getInstance().timeInMillis

}