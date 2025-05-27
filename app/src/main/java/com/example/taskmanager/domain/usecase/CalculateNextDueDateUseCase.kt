package com.example.taskmanager.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskmanager.data.local.Repeat
import java.time.LocalDate

class CalculateNextDueDateUseCase() {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(currentDate: LocalDate, repeatType: Repeat): LocalDate{
        return when(repeatType){
            Repeat.Daily -> currentDate.plusDays(1)
            Repeat.Weekly -> currentDate.plusWeeks(1)
            Repeat.Monthly -> currentDate.plusMonths(1)

//            Will never be executed
            else -> {LocalDate.now()}
        }
    }
}