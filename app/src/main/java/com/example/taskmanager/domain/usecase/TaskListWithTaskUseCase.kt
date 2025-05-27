package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks

class TaskListWithTaskUseCase {
    operator fun invoke(taskListWithTasks: List<TaskListWithTasks>, taskList: TaskList): List<Task> {
        taskListWithTasks.forEach { it ->
            if(it.taskList == taskList){
                return it.task
            }
        }
        return emptyList()
    }
}