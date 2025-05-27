package com.example.taskmanager.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverters
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class LocalDateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverters
    fun fromEpochMillis(value: Long?): LocalDate?{
        return value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverters
    fun toEpochMillis(date: LocalDate?): Long? {
        return date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
}