package com.example.taskmanager.data.repository

import android.util.Log
import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.toDomain
import com.example.taskmanager.data.local.toEntity
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks
import com.example.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {

    override fun getAllCompleteTasks(): Flow<List<Task>> = taskDao.getAllCompleteTasks().map { list -> list.map{ it.toDomain()} }

    override fun getAllIncompleteTasks(): Flow<List<Task>> = taskDao.getAllIncompleteTasks().map { list ->  list.map { it.toDomain() }}


    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun insertTask(task: Task) {
        Log.d("AddedTask", task.toString())
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun insertTaskList(taskList: TaskList) {
        taskDao.insertTaskList(taskList.toEntity())
    }

    override suspend fun updateTaskList(taskList: TaskList) {
        taskDao.updateTaskList(taskList.toEntity())
    }

    override suspend fun deleteTaskList(taskList: TaskList) {
        taskDao.deleteTaskList(taskList.toEntity())
    }

    override fun getAllTaskList(): Flow<List<TaskList>> = taskDao.getAllTaskList().map { list -> list.map { it.toDomain() } }

    override fun getAllTasksAndTaskList(): Flow<List<TaskListWithTasks>> = taskDao.getAllTasksAndTaskLists().map { list -> list.map { it.toDomain() } }
}

