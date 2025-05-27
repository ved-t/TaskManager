package com.example.taskmanager.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverters
import java.time.LocalTime

class LocalTimeConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverters
    fun fromMinutesSinceMidnight(minutes: Int?): LocalTime?{
        return minutes?.let { LocalTime.of(it/60, it%60) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverters
    fun toMinutesSinceMidnight(time: LocalTime?): Int?{
        return time?.let { it.hour*60 + it.minute }
    }
}