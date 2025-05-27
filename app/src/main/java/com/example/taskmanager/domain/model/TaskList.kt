package com.example.taskmanager.domain.model

data class TaskList(
    val id: Int = 0,
    val listName: String,
    val isDefault: Boolean = false
)