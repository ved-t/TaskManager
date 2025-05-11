package com.example.taskmanager.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.OpenBottomSheet
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.presentation.viewmodel.TaskViewModel

@Composable
fun TaskScreen(paddingValues: PaddingValues,viewModel: TaskViewModel = hiltViewModel() ){

    val allTasks by viewModel.allTasks.collectAsState()

    Log.d("TaskList", allTasks.toString())

    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(allTasks){task ->
            TaskItem(task)
            Spacer(modifier = Modifier.height(8.dp ))
        }
    }
}

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
            .height(70.dp)
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
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if(!isLongPressed){
                Column {
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
                        OpenBottomSheet(onDismiss = {showSheet = false}, "TaskUpdatingArea", task)
                    }
                }
            }
            else{
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        Icons.Rounded.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        }
    }
}