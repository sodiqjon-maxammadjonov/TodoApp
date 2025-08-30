package com.sdk.todoapp.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatRelativeDate(timestamp: Long): String {
        val date = Date(timestamp)
        val now = Date()
        val calendar = Calendar.getInstance()

        val todayStart = calendar.apply {
            time = now
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val yesterdayStart = todayStart - 24 * 60 * 60 * 1000

        return when {
            timestamp >= todayStart -> {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
            }
            timestamp >= yesterdayStart -> {
                "Kecha ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)}"
            }
            else -> {
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
            }
        }
    }

    fun formatFullDate(timestamp: Long): String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
    }

    fun formatTime(timestamp: Long): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
    }

    fun isToday(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR)
        val todayYear = calendar.get(Calendar.YEAR)

        calendar.timeInMillis = timestamp
        val targetDay = calendar.get(Calendar.DAY_OF_YEAR)
        val targetYear = calendar.get(Calendar.YEAR)

        return today == targetDay && todayYear == targetYear
    }
}