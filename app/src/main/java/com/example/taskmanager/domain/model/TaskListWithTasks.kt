package com.example.taskmanager.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskmanager.data.local.TaskEntity


data class TaskListWithTasks (
    val taskList: TaskList,
    val task: List<Task>
)