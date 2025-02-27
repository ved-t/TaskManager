package com.example.taskmanager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskManager(
    private val taskDao: TaskDao
) {

    fun addTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.updateTask(task)
        }
    }

    fun removeTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.deleteTask(task)
        }
    }
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
}

