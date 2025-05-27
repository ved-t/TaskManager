package com.example.taskmanager.presentation.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.DatePickerModal
import com.example.taskmanager.data.local.Priority
import com.example.taskmanager.data.local.Repeat
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.presentation.viewmodel.TaskViewModel
import com.example.taskmanager.TimePickerModal
import com.example.taskmanager.domain.model.TaskList
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddDetailArea(
    taskTitle: String,
    taskDescription: String,
    taskPriority: Priority,
    onDismiss: () -> Unit,
    onDismissHalfDialogBox: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
){
    var title by remember { mutableStateOf(taskTitle) }
    var description by remember { mutableStateOf(taskDescription) }

    val isTitleEmpty = remember{ mutableStateOf(title.isEmpty()) }
    val isDescriptionEmpty = remember{ mutableStateOf(description.isEmpty()) }

    var textFieldColors by remember {
        mutableStateOf(Color.White)
    }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        Header Text
        Text(
            text = "Add new task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp ))

//        Title TextField
        TextField(
            value = title,
            onValueChange = {
                title = it
                isTitleEmpty.value = it.isEmpty()
            },
            label = { Text("Title") },
            placeholder = { Text("Enter title") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = textFieldColors,
                unfocusedIndicatorColor = textFieldColors,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp ))

//        Description TextField
        TextField(
            value = description,
            onValueChange = {
                description = it
                isDescriptionEmpty.value = it.isEmpty()
            },
            label = { Text("Description") },
            placeholder = { Text("Enter description") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = textFieldColors,
                unfocusedIndicatorColor = textFieldColors,
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp ))

//        Priority TextField
        var expanded by remember { mutableStateOf(false ) }
        var selectedOption by remember { mutableStateOf(taskPriority) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOption.name,
                    onValueChange = {},
                    label = { Text("Priority") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {
                    Priority.entries.forEach { value ->
                        DropdownMenuItem(
                            text = { Text(value.name) },
                            onClick = {
                                selectedOption = value
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp ))

//        Date Picker & Date Display OutlinedTextField
        var date: Long? = viewModel.dateMillis
        var time: Int? = viewModel.startTime

        var showDatePickerModal by remember { mutableStateOf(false) }

        val displayDate = viewModel.selectedDate.collectAsState()

        OutlinedTextField(
            value = displayDate.value,
            onValueChange = {},
            label = { Text(text = "Date") },
            trailingIcon = {
                Icon(
                    Icons.Filled.CalendarMonth,
                    contentDescription = "Calender Icon",
                    Modifier.clickable {
                        showDatePickerModal = true
                    }
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        if(showDatePickerModal) {
            DatePickerModal(
                onDateSelected = {
                    viewModel.updateDate(it)
                    date = it
                    showDatePickerModal = false
                },
                onDismiss = {
                    showDatePickerModal = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp ))

//        Time Picker & TimePicker OutlinedTextField
        var showTimePickerModal by remember { mutableStateOf(false  ) }
        val displayTime = viewModel.displayTime.collectAsState()

        OutlinedTextField(
            value = displayTime.value,
            onValueChange = {},
            label = { Text(text = "Time") },
            trailingIcon = {
                Icon(
                    Icons.Filled.AccessTime,
                    contentDescription = "Access Time Icon",
                    Modifier.clickable {
                        showTimePickerModal = true
                    }
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        if(showTimePickerModal){
            TimePickerModal(
                onConfirm = {
                    viewModel.updateTime(
                        it.hour,
                        it.minute
                    )
                    time = viewModel.localTimeToMinutes(LocalTime.of(it.hour, it.minute))
                    showTimePickerModal = false
                },
                onDismiss = {showTimePickerModal = false}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var repeatExpanded by remember { mutableStateOf(false) }
        var selectedRepeatFrequency by remember { mutableStateOf(Repeat.None) }
        var openCustomRepeatPage by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = repeatExpanded,
            onExpandedChange = { repeatExpanded = !repeatExpanded },
        ) {
            OutlinedTextField(
                value = selectedRepeatFrequency.name,
                onValueChange = {},
                label = { Text(text = "Repeat") },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Repeat,
                        contentDescription = "Repeat Icon",
                        Modifier.clickable {
                            repeatExpanded = true
                        }
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
            )
            ExposedDropdownMenu(
                expanded = repeatExpanded,
                onDismissRequest = { repeatExpanded = false }
            ) {
                Repeat.entries.forEach { value ->
                    DropdownMenuItem(
                        text = { Text(value.name) },
                        onClick = {
                            selectedRepeatFrequency = value
                            when(value){
                                Repeat.None -> {}
                                Repeat.Daily -> {}
                                Repeat.Weekly -> {}
                                Repeat.Monthly -> {}
                                Repeat.Custom -> {
                                    openCustomRepeatPage = true
                                }
                            }

                            repeatExpanded = false
                        }
                    )
                }
            }
        }

        if(openCustomRepeatPage){
            RepeatTaskDialog(
                onDismiss = {openCustomRepeatPage = false}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var selectedTaskListId by remember { mutableStateOf<Int?> (null) }
        var selectedTaskListName by remember { mutableStateOf<String> ("Select Task List") }
        var taskListExpanded by remember { mutableStateOf(false) }

        val allTaskList by viewModel.allTasksList.collectAsState()


        ExposedDropdownMenuBox(
            expanded = taskListExpanded,
            onExpandedChange = { taskListExpanded = !taskListExpanded },
        ) {
            OutlinedTextField(
                value = selectedTaskListName,
                onValueChange = {},
                label = { Text(text = "Task list") },
                trailingIcon = {
                    Icon(
                        Icons.Filled.EditNote ,
                        contentDescription = "Task list Icon",
                        Modifier.clickable {
                            taskListExpanded = true
                        }
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
            )
            ExposedDropdownMenu(
                expanded = taskListExpanded,
                onDismissRequest = { taskListExpanded = false }
            ) {
                allTaskList.forEach { taskList ->
                    DropdownMenuItem(
                        text = { Text(taskList.listName ) },
                        onClick = {
                            selectedTaskListId = taskList.id
                            selectedTaskListName = taskList.listName
                            taskListExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if(!isTitleEmpty.value && !isDescriptionEmpty.value){
                    title = viewModel.cleanString(title)
                    description = viewModel.cleanString(description)

                    val taskListId = selectedTaskListId
                    val isRepeating = selectedRepeatFrequency != Repeat.None

                    val task: Task = Task(
                        title = title,
                        description = description,
                        priority = selectedOption,
                        dueDate = date!!,
                        dueTime = time!!,
                        repeat = selectedRepeatFrequency,
                        isRepeating = isRepeating,
                        taskListId = taskListId
                    )

                    viewModel.addTask(task)
                    onDismiss()
                    onDismissHalfDialogBox()
                    Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_LONG).show()
                }
                else{
                    textFieldColors = Color.Red
                    Toast.makeText(context, "Please enter valid Title", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Add")
            }
            Button(onClick = {
                onDismiss()
                onDismissHalfDialogBox()
            }) {
                Text("Close")
            }
        }
    }
}