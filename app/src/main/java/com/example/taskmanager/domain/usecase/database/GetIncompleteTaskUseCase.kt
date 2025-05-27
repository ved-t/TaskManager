package com.example.taskmanager.domain.usecase.database

import com.example.taskmanager.domain.repository.TaskRepository

class GetIncompleteTaskUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAllIncompleteTasks()
}