package com.example.taskmanager.domain.model

import com.example.taskmanager.data.local.Priority

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
//    val date: Date // ADD DATE FUNCTIONALITY
)