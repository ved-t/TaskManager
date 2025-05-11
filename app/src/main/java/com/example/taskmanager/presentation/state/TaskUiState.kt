package com.example.taskmanager.presentation.state

import com.example.taskmanager.domain.model.Task

data class TaskUiState (
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
)