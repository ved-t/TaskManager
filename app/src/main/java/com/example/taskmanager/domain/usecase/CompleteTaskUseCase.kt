package com.example.taskmanager.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskmanager.data.local.Repeat
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.usecase.converters.LocalDateToMillisUseCase
import com.example.taskmanager.domain.usecase.converters.MillisToLocalDateUseCase
import java.time.LocalDate

class CompleteTaskUseCase(
    private val calculateNextDueDateUseCase: CalculateNextDueDateUseCase,
    private val localDateToMillisUseCase: LocalDateToMillisUseCase,
    private val longToLocalDateUseCase: MillisToLocalDateUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(task: Task): () -> Task {
        return {
            if(task.isRepeating){

                val currentDate: LocalDate? = longToLocalDateUseCase(task.dueDate)
                val repeatType: Repeat = task.repeat

                val newLocalDate: LocalDate? = currentDate?.let { calculateNextDueDateUseCase(it, repeatType) }

                val newLongDate: Long? = localDateToMillisUseCase(newLocalDate)

                task.copy(isComplete = false, dueDate = newLongDate!!)
            }
            else{
                task.copy(isComplete = true, dueDate = 0, dueTime = 0)
            }
        }
    }
}