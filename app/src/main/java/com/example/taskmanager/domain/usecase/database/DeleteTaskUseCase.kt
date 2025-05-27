package com.example.taskmanager.domain.usecase.database

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repository.TaskRepository

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) = taskRepository.deleteTask(task)
}