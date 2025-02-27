package com.example.taskmanager

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

@Composable
fun NavigationDrawer(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val database: TaskDatabase = remember { TaskDatabase.getDatabase(context) }
    val taskManager: TaskManager = remember { TaskManager(database.taskDoa()) }

//    For Task Adding Pop-Up
    var showSheet by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("My App", style = MaterialTheme.typography.bodyMedium)
            }
        },
    ) {
        Scaffold(
            topBar = { MyTopAppBar(scope, drawerState) },
            bottomBar = { MyBottomAppBar()},
            floatingActionButton = {MyFloatingActionButton { showSheet = true } },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            TaskScreen(innerPadding, context, taskManager)
        }
    }

    if(showSheet){
        OpenBottomSheet(showSheet, onDismiss =  {showSheet = false},"TaskAddingArea", taskManager)
    }

}


@Composable
fun OpenBottomSheet(showSheet: Boolean, onDismiss: ()->Unit, sheetType: String, taskManager: TaskManager, task: Task? = null){
    if(showSheet){
        if(sheetType == "TaskAddingArea"){
            FABWithBottomSheet(showSheet, onDismiss =  onDismiss
            ) { TaskAddingArea(onDismiss = onDismiss, taskManager::addTask) }
        }
        else if(sheetType == "TaskUpdatingArea"){
            FABWithBottomSheet(showSheet, onDismiss =  onDismiss
            ) { TaskUpdatingArea(onDismiss = onDismiss, taskManager::addTask, task) }
        }
    }
}