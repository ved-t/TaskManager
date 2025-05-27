package com.example.taskmanager.domain.model

import com.example.taskmanager.data.local.Priority
import com.example.taskmanager.data.local.Repeat

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: Long = 0,
    val dueTime: Int = 0,

    val repeat: Repeat = Repeat.None,
    val isRepeating: Boolean = false,

    val isComplete: Boolean = false,

    val taskListId: Int? = null
)