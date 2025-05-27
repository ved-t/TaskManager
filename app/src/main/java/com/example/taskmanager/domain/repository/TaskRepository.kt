package com.example.taskmanager.domain.repository

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks
import kotlinx.coroutines.flow.Flow

interface TaskRepository{
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    fun getAllCompleteTasks(): Flow<List<Task>>

    fun getAllIncompleteTasks(): Flow<List<Task>>

    suspend fun insertTaskList(taskList: TaskList)

    suspend fun updateTaskList(taskList: TaskList)

    suspend fun deleteTaskList(taskList: TaskList)

    fun getAllTaskList(): Flow<List<TaskList>>

    fun getAllTasksAndTaskList(): Flow<List<TaskListWithTasks>>
}