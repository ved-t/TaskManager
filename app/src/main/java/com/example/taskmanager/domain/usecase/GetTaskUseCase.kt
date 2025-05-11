package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.repository.TaskRepository

class GetTaskUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAllTasks()
}