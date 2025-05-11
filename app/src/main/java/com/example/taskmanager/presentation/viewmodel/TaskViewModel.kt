package com.example.taskmanager.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.usecase.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.GetTaskUseCase
import com.example.taskmanager.domain.usecase.InsertTaskUseCase
import com.example.taskmanager.domain.usecase.UpdateTaskUseCase
import com.example.taskmanager.presentation.state.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasks: GetTaskUseCase,
    private val insertTask: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTask: DeleteTaskUseCase
): ViewModel(){
    var isLoading by mutableStateOf(false)

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    init {
        loadTasks()
    }

    private fun loadTasks(){
        viewModelScope.launch {
            isLoading = true
            try {
                getTasks().collect{ tasks ->
                    _allTasks.value = tasks
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            finally {
                isLoading = false
            }
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch {
            insertTask(task)
            loadTasks()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            updateTaskUseCase(task)
            loadTasks()
        }
    }

    fun removeTask(task: Task){
        viewModelScope.launch {
            deleteTask(task)
            loadTasks()
        }
    }
}