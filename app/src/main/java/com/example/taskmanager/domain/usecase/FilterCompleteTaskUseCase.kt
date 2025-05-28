package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task

class FilterCompleteTaskUseCase {
    operator fun invoke(tasks: List<Task>): List<Task> = tasks.filter { it.isComplete }
}