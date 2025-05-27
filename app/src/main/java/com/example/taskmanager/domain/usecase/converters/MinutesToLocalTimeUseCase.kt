package com.example.taskmanager.domain.usecase.converters

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskmanager.core.util.LocalTimeConverter

class MinutesToLocalTimeUseCase(private val timeConverter: LocalTimeConverter) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(minutes: Int) = timeConverter.fromMinutesSinceMidnight(minutes)
}