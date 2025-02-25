package com.example.taskmanager

import kotlinx.coroutines.flow.Flow

class TaskManager(
    private val taskDao: TaskDao
) {
    suspend fun addTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun removeTask(task: Task) = taskDao.deleteTask(task)
    suspend fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
}
