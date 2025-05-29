package com.example.taskmanager.presentation.ui.tasklist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.Ref
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.presentation.ui.common.HalfDialogBox
import com.example.taskmanager.presentation.viewmodel.task.TaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreListOptionsMenu(taskList: TaskList) {
    var expanded by remember { mutableStateOf(false) }
    val iconButtonPosition = remember { Ref<Offset>() }

    var showTaskListAddDialog by remember { mutableStateOf(false) }
    var showDeleteDialogBox by remember { mutableStateOf(false) }

    Box{
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "More List features",
            modifier = Modifier
                .clickable {
                    expanded = true
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            offset = DpOffset(x = 0.dp, y = 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Change list name") },
                onClick = {
                    showTaskListAddDialog = true
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        "Delete",
                        color = Color.Red
                    )
                },
                onClick = {
                    showDeleteDialogBox = true
                    expanded = false
                }
            )
        }

        if(showTaskListAddDialog){
            TaskListDialogBox(taskList = taskList, {showTaskListAddDialog = false})
        }

        if(showDeleteDialogBox){
            DeleteTaskListDialogBox(taskList, {showDeleteDialogBox = false})
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeleteTaskListDialogBox(
    taskList: TaskList,
    onDismiss: () -> Unit
){
    val viewModel: TaskViewModel = hiltViewModel()

    HalfDialogBox(onDismiss = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "Delete task list"
                )
            }

            Spacer(modifier = Modifier.height(16.dp ))

            Text(
                text = "Deleting task list will also delete tasks within the task list.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp ))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    viewModel.deleteTaskList(taskList)
                    onDismiss()
                }) {
                    Text(
                        text = "Delete",
                        color = Color.Red
                    )
                }
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    }
}