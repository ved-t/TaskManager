package com.example.taskmanager.presentation.ui.task

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.taskmanager.data.local.Priority
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.presentation.ui.common.FullScreenDialogBox
import com.example.taskmanager.presentation.viewmodel.task.TaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddingArea(onDismiss: () -> Unit, viewModel: TaskViewModel = hiltViewModel()){

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isTitleEmpty = remember{ mutableStateOf(true)}
    val isDescriptionEmpty = remember{ mutableStateOf(true)}

    var textFieldColors by remember {
        mutableStateOf(Color.White)
    }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Add new task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp ))

        TextField(
            value = title,
            onValueChange = {
                title = it
                isTitleEmpty.value = it.isEmpty()
            },
            singleLine = true,
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

        var expanded by remember { mutableStateOf(false ) }
        var selectedOption by remember { mutableStateOf(Priority.MEDIUM) }

        var openTaskDetailPage by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                    label = { Text("Priority")},
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

            Icon(
                Icons.TwoTone.Menu ,
                contentDescription = "More",
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.DarkGray)
                    .clickable {
                        openTaskDetailPage = true
                        Toast.makeText(context, "Add more information", Toast.LENGTH_LONG).show()
                    }
            )
        }

        if(openTaskDetailPage){
            FullScreenDialogBox(
                onDismiss
            ) {
                TaskAddDetailArea(
                    taskTitle = title,
                    taskDescription = description,
                    taskPriority = selectedOption,
                    onDismiss = {
                        openTaskDetailPage = false
                    },
                    onDismissHalfDialogBox = onDismiss
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp ))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if(!isTitleEmpty.value && !isDescriptionEmpty.value){
                    title = viewModel.cleanString(title)
                    description = viewModel.cleanString(description)

                    val task: Task = Task(
                        title = title,
                        description = description,
                        priority = selectedOption,
                    )

                    viewModel.addTask(task)
                    onDismiss()
                    Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_LONG).show()
                }
                else{
                    textFieldColors = Color.Red
                    Toast.makeText(context, "Please enter valid Title", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Add")
            }
            Button(onClick = onDismiss) {
                Text("Close")
            } 
        }
    }
}

