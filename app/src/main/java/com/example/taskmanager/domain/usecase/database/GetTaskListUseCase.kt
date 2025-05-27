package com.example.taskmanager.domain.usecase.database

import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.repository.TaskRepository

class GetTaskListUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getAllTaskList()
}