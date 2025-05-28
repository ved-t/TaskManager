package com.example.taskmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.presentation.ui.FullScreenDialogBox
import com.example.taskmanager.presentation.ui.HalfDialogBox
import com.example.taskmanager.presentation.ui.TaskAddingArea
import com.example.taskmanager.presentation.ui.TaskDetailPage
import com.example.taskmanager.presentation.ui.TaskListHeader
import com.example.taskmanager.presentation.ui.TaskScreen
import com.example.taskmanager.presentation.ui.TaskUpdatingArea

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationDrawer(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

//    For Task Adding Pop-Up
    var showHalfDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { MyTopAppBar(scope, drawerState) },
        bottomBar = { MyBottomAppBar()},
        floatingActionButton = {MyFloatingActionButton { showHalfDialog = true } },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        TaskListHeader(innerPadding)
    }

    if(showHalfDialog){
        OpenDialogBox(onDismiss =  {showHalfDialog = false},"TaskAddingArea")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenDialogBox(onDismiss: ()->Unit, dialogType: String, task: Task? = null ){
    if(dialogType == "TaskAddingArea"){
        HalfDialogBox(onDismiss) {
            TaskAddingArea(onDismiss)
        }
    }
    else if(dialogType == "TaskUpdatingArea"){
        FullScreenDialogBox(
            onDismiss
        ) {
            TaskUpdatingArea(onDismiss, task)
        }
    }
}

//Bottom sheet pop up
/*
@Composable
fun OpenBottomSheet(onDismiss: ()->Unit, sheetType: String, task: Task? = null ){
    if(sheetType == "TaskAddingArea"){
        BottomSheet(
            onDismiss = onDismiss
        ) {
            TaskAddingArea(onDismiss = onDismiss)
        }
    }
    else if(sheetType == "TaskUpdatingArea"){
        BottomSheet(
            onDismiss = onDismiss
        ) {
            TaskUpdatingArea(onDismiss = onDismiss, task)
        }
    }
}*/
