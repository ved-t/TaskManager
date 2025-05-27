package com.example.taskmanager.domain.usecase.database

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.TaskRepository

class GetCompleteTaskUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAllCompleteTasks()
}