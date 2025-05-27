package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task

class FilterIncompleteCompleteTasksUseCase {
    operator fun invoke(tasks: List<Task>): Pair<List<Task>, List<Task>>{
        val inCompleteTasks: List<Task> = tasks.filter { !it.isComplete }
        val completeTasks: List<Task> = tasks.filter { it.isComplete }

        return Pair(inCompleteTasks, completeTasks)
    }
}