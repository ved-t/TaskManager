package com.example.taskmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.ui.theme.TaskManagerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                NavigationDrawer()
            }
        }
    }
}

@Composable
fun TaskScreen(paddingValues: PaddingValues, context: Context, taskManager: TaskManager){
    var taskList = remember { mutableStateListOf(emptyList<Task>()) }
    LaunchedEffect(Unit) {
        taskManager.getAllTasks().collect{tasks->
            taskList.clear()
            taskList.addAll(listOf(tasks))
        }
    }

    val flattenedList = taskList.flatten()
    Log.d("TaskList", flattenedList.toString())

    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(flattenedList){task ->
            TaskItem(task, taskManager)
            Spacer(modifier = Modifier.height(8.dp ))
        }
    }
}

@Composable
fun TaskItem(task: Task, taskManager: TaskManager) {
    var showSheet by remember { mutableStateOf(false)}
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                showSheet = true
            }
    ){
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
            OpenBottomSheet(showSheet, onDismiss = {showSheet = false}, "TaskUpdatingArea", taskManager, task)
        }
    }
}


