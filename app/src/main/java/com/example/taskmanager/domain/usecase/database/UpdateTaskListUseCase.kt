package com.example.taskmanager.domain.usecase.database

import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.repository.TaskRepository

class UpdateTaskListUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskList: TaskList) = taskRepository.updateTaskList(taskList)
}