package com.example.taskmanager.presentation.ui

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.OpenDialogBox
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.presentation.viewmodel.TaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListHeader(
    paddingValues: PaddingValues,
    viewModel: TaskViewModel = hiltViewModel()
){
    val allTaskLists by viewModel.allTasksList.collectAsState()
    val context = LocalContext.current
    var showTaskListAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Filled.StarOutline,
                    contentDescription = "Starred Tasks"
                )
            }

            Spacer(Modifier.padding(8.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                items(allTaskLists){taskList ->
                    TaskListItem(taskList)
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                TextButton(onClick = {
                    showTaskListAddDialog = !showTaskListAddDialog
                    Toast.makeText(context, "Add new Task List", Toast.LENGTH_SHORT).show()
                }) {
                    Text(
                        text = "New List",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if(showTaskListAddDialog){
                TaskListAddDialogBox(onDismiss = {showTaskListAddDialog = false})
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TaskScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskListItem(taskList: TaskList, viewModel: TaskViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .combinedClickable (
                onClick = {
                    viewModel.updateTasksWithTaskList(taskList)
                }
            )
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
    ) {
        Text(
            text = taskList.listName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(4.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel() ){

    val allIncompleteTasks by viewModel.allIncompleteTasks.collectAsState()
    val allCompleteTasks by viewModel.allCompleteTasks.collectAsState()

    val incompleteTasksSize = allIncompleteTasks.size
    val completeTasksSize = allCompleteTasks.size

    var isShowIncompleteTasksExpanded by remember { mutableStateOf(false) }
    var isShowCompleteTasksExpanded by remember { mutableStateOf(false) }

    val taskList by viewModel.taskList.collectAsState()

    Column(
        modifier = Modifier
    ) {
        if(taskList!=null){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Text(
                    text = taskList!!.listName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "More List features",
                    modifier = Modifier
                        .clickable {
                        }
                )
            }
        }
        if(incompleteTasksSize > 0){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                        isShowIncompleteTasksExpanded = !isShowIncompleteTasksExpanded
                    }
            ){
                Text(
                    text = "Incomplete Tasks",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )

                if(!isShowIncompleteTasksExpanded){
                    Icon(
                        Icons.Filled.ExpandMore,
                        contentDescription = "Expand Incomplete Tasks",
                        modifier = Modifier
                            .clickable {
                                isShowIncompleteTasksExpanded = true
                            }
                    )
                }
                else{
                    Icon(
                        Icons.Filled.ExpandLess,
                        contentDescription = "Minimize Incomplete Tasks",
                        modifier = Modifier
                            .clickable {
                                isShowIncompleteTasksExpanded = false
                            }
                    )
                }
            }
        }

        if(isShowIncompleteTasksExpanded){
            LazyColumn(
                modifier = Modifier
//                    .background(Color.LightGray)
            ) {
                items(allIncompleteTasks){ task ->
                    TaskItem(task)
                    Spacer(modifier = Modifier.height(8.dp ))
                }
            }
        }

        if(completeTasksSize > 0){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                        isShowCompleteTasksExpanded = !isShowCompleteTasksExpanded
                    }
            ){
                Text(
                    text = "Complete Tasks",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )

                if(!isShowCompleteTasksExpanded){
                    Icon(
                        Icons.Filled.ExpandMore,
                        contentDescription = "Expand Complete Tasks",
                        modifier = Modifier
                            .clickable {
                                isShowCompleteTasksExpanded = true
                            }
                    )
                }
                else{
                    Icon(
                        Icons.Filled.ExpandLess,
                        contentDescription = "Minimize Complete Tasks",
                        modifier = Modifier
                            .clickable {
                                isShowCompleteTasksExpanded = false
                            }
                    )
                }
            }
        }

        if(isShowCompleteTasksExpanded){
            LazyColumn(
                modifier = Modifier
//                    .background(Color.LightGray)
            ) {
                items(allCompleteTasks){ task ->
                    TaskItem(task)
                    Spacer(modifier = Modifier.height(8.dp ))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(task: Task) {
    var showSheet by remember { mutableStateOf(false) }

    val haptics = LocalHapticFeedback.current

    var isLongPressed by remember { mutableStateOf(false) }

    val cardColor by animateColorAsState(
        targetValue = if(isLongPressed) Color.Red else Color.Black,
        label = "backGroundColorAnimation"
    )

    val viewModel: TaskViewModel = hiltViewModel()
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .combinedClickable(
                onClick = {
                    if(!isLongPressed){
                        showSheet = true
                    }
                    else{
                        viewModel.removeTask(task)
                        Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show()
                        isLongPressed = false
                    }
                },
                onLongClick = {
                    isLongPressed  = !isLongPressed
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.1f)
            ) {
                if(!task.isComplete){
                    Icon(
                        Icons.Filled.CheckCircleOutline,
                        contentDescription = "Incomplete Task Icon",
                        modifier = Modifier
                            .clickable {
                                viewModel.completeTask(task)
                                Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
                else{
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Complete Task Icon",
                        modifier = Modifier
                            .clickable {
                                viewModel.updateTask(task.copy(isComplete = false))
                                Toast.makeText(context, "Task marked Incomplete", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = task.description,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                if(showSheet){
                    OpenDialogBox (onDismiss = {showSheet = false}, "TaskUpdatingArea", task)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if(!isLongPressed){
                    Icon(
                        Icons.Rounded.Info,
                        contentDescription = "Info Icon"
                    )
                }
                else{
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        }
    }
}