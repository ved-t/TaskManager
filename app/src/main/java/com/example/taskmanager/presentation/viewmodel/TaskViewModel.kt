package com.example.taskmanager.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.domain.model.TaskListWithTasks
import com.example.taskmanager.domain.usecase.CompleteTaskUseCase
import com.example.taskmanager.domain.usecase.FilterCompleteTaskUseCase
import com.example.taskmanager.domain.usecase.TaskListWithTaskUseCase
import com.example.taskmanager.domain.usecase.database.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.GetIncompleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.InsertTaskUseCase
import com.example.taskmanager.domain.usecase.converters.LocalDateToMillisUseCase
import com.example.taskmanager.domain.usecase.converters.LocalTimeToMinutesUseCase
import com.example.taskmanager.domain.usecase.converters.MillisToLocalDateUseCase
import com.example.taskmanager.domain.usecase.converters.MinutesToLocalTimeUseCase
import com.example.taskmanager.domain.usecase.database.DeleteTaskListUseCase
import com.example.taskmanager.domain.usecase.database.GetCompleteTaskUseCase
import com.example.taskmanager.domain.usecase.database.GetTaskListAndTasksUseCase
import com.example.taskmanager.domain.usecase.database.GetTaskListUseCase
import com.example.taskmanager.domain.usecase.database.InsertTaskListUseCase
import com.example.taskmanager.domain.usecase.database.UpdateTaskListUseCase
import com.example.taskmanager.domain.usecase.utils.TrimWhiteSpacesUseCase
import com.example.taskmanager.domain.usecase.database.UpdateTaskUseCase
import com.example.taskmanager.domain.usecase.FilterIncomepleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getIncompleteTaskUseCase: GetIncompleteTaskUseCase,
    private val getCompleteTaskUseCase: GetCompleteTaskUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,
    private val getTaskListAndTasksUseCase: GetTaskListAndTasksUseCase,
    private val insertTaskListUseCase: InsertTaskListUseCase,
    private val updateTaskListUseCase: UpdateTaskListUseCase,
    private val deleteTaskListUseCase: DeleteTaskListUseCase,
    private val insertTask: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val trimWhiteSpaces: TrimWhiteSpacesUseCase,
    private val millisToLocalDate: MillisToLocalDateUseCase,
    private val localDateToMillis: LocalDateToMillisUseCase,
    private val minutesToLocalTimeUseCase: MinutesToLocalTimeUseCase,
    private val localTimeToMinutesUseCase: LocalTimeToMinutesUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val taskListWithTaskUseCase: TaskListWithTaskUseCase,
    private val filterIncomepleteTaskUseCase: FilterIncomepleteTaskUseCase,
    private val filterCompleteTaskUseCase: FilterCompleteTaskUseCase,
): ViewModel(){
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    private val _startDate = MutableStateFlow<LocalDate>(LocalDate.now())

    private val _dateMillis = MutableStateFlow<Long?>(toMillis(_startDate.value))

    var dateMillis: Long? = toMillis(_startDate.value)

    val selectedDate: StateFlow<String> = _dateMillis
        .map { millis ->
            toLocalDate(millis)?.format(dateFormatter) ?: ""
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _startDate.value.format(dateFormatter)
        )

    
    private val _startTime = MutableStateFlow<LocalTime>(LocalTime.now())

    var startTime: Int? = localTimeToMinutes(_startTime.value)

    val displayTime: StateFlow<String> = _startTime
        .map { time ->
            timeFormatter.format(time)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _startTime.value.format(timeFormatter)
        )

    fun updateTime(hour: Int, minute: Int){
        val time = LocalTime.of(hour, minute)
        _startTime.value = time
        startTime = localTimeToMinutes(_startTime.value)
    }

    fun getTimeFromTask(task: Task){
        if(task.dueTime == 0){
            _startTime.value = LocalTime.now()
            startTime = localTimeToMinutes(_startTime.value)
        }
        else{
            _startTime.value = minutesToLocalTime(task.dueTime)!!
            startTime = localTimeToMinutes(_startTime.value)
        }
    }

    fun localTimeToMinutes(time: LocalTime?): Int?{
        return time?.let { localTimeToMinutesUseCase(time) }
    }

    private fun minutesToLocalTime(minutes: Int?): LocalTime? {
        return minutes?.let { minutesToLocalTimeUseCase(minutes) }
    }
    
    fun updateDate(millis: Long?){
        _dateMillis.value = millis
        dateMillis = _dateMillis.value
    }

    fun getDateFromTask(task: Task){
        if(task.dueDate.toInt() == 0){
            _dateMillis.value = toMillis(_startDate.value)
            dateMillis = _dateMillis.value
        }
        else{
            _dateMillis.value = task.dueDate
            dateMillis = _dateMillis.value
        }
    }

    private fun toLocalDate(millis: Long?): LocalDate? {
        return millis?.let { millisToLocalDate(it) }
    }

    private fun toMillis(date: LocalDate?): Long?{
        return date?.let { localDateToMillis(date) }
    }

    private val _allIncompleteTasks = MutableStateFlow<List<Task>>(emptyList())
    val allIncompleteTasks: StateFlow<List<Task>> = _allIncompleteTasks

    private val _allCompleteTasks = MutableStateFlow<List<Task>>(emptyList())
    val allCompleteTasks: StateFlow<List<Task>> = _allCompleteTasks

    private val _allTasksList = MutableStateFlow<List<TaskList>>(emptyList())
    val allTasksList: StateFlow<List<TaskList>> = _allTasksList

    private val _taskListAndTask = MutableStateFlow<List<TaskListWithTasks>>(emptyList())

    private val _taskList = MutableStateFlow<TaskList?>(null)
    val taskList: StateFlow<TaskList?> = _taskList

    val incompleteTasks: StateFlow<List<Task>> = combine (
        _allIncompleteTasks,
        _taskList
    ){incompleteList, list ->
        val listId = list?.id
        if(listId == null) incompleteList
        else incompleteList.filter { it.taskListId == listId}
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList<Task>()
    )

    val completeTasks: StateFlow<List<Task>> = combine (
        _allCompleteTasks,
        _taskList
    ){incompleteList, list ->
        val listId = list?.id
        if(listId == null) incompleteList
        else incompleteList.filter { it.taskListId == listId}
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList<Task>()
    )

    init {
        loadAll()
    }

    private fun loadAll(){
        loadIncompleteTasks()
        loadCompleteTasks()
        loadTaskList()
        loadTaskListAndTasks()
    }

    private fun loadIncompleteTasks(){
        viewModelScope.launch {
            try {
                getIncompleteTaskUseCase().collect{ tasks ->
                    _allIncompleteTasks.value = tasks
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun loadCompleteTasks(){
        viewModelScope.launch {
            try {
                getCompleteTaskUseCase().collect{ tasks ->
                    _allCompleteTasks.value = tasks
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun loadTaskListAndTasks(){
        viewModelScope.launch {
            try {
                getTaskListAndTasksUseCase().collect{ taskListAndTasks->
                    _taskListAndTask.value = taskListAndTasks
                }
            } 
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun loadTaskList(){
        viewModelScope.launch {
            try {
                getTaskListUseCase().collect{ taskList ->
                    _allTasksList.value = taskList
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateTasksWithTaskList(taskList: TaskList){
        _taskList.value = taskList
    }

    fun addTask(task: Task){
        viewModelScope.launch {
            insertTask(task.copy(id = 0))
            loadAll()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            updateTaskUseCase(task)
            loadAll()
        }
    }

    fun removeTask(task: Task){
        viewModelScope.launch {
            deleteTask(task)
            loadAll()
        }
    }

    fun addTaskList(taskList: TaskList){
        viewModelScope.launch {
            insertTaskListUseCase(taskList)
            loadAll()
        }
    }

    fun updateTaskList(taskList: TaskList){
        viewModelScope.launch {
            updateTaskListUseCase(taskList)
            loadAll()
        }
    }

    fun deleteTaskList(taskList: TaskList){
        viewModelScope.launch {
            deleteTaskListUseCase(taskList)
            loadAll()
        }
    }

    fun cleanString(string: String): String = trimWhiteSpaces(string)

    fun completeTask(task: Task){
        viewModelScope.launch {
            val newTask = completeTaskUseCase(task)

            if(task.isRepeating){
                updateTask(task.copy(isComplete = true))
                addTask(newTask())
            }
            else{
                updateTask(newTask())
            }
        }
    }
}