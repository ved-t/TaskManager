package com.example.taskmanager.domain.usecase.converters

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskmanager.core.util.LocalDateConverter

class MillisToLocalDateUseCase(private val localDateConverter: LocalDateConverter) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(millis: Long) = localDateConverter.fromEpochMillis(millis)
}