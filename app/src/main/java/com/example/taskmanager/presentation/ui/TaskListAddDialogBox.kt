package com.example.taskmanager.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.taskmanager.domain.model.TaskList
import com.example.taskmanager.presentation.viewmodel.TaskViewModel

@Composable
fun TaskListAddDialogBox(
    onDismiss: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel()
){
    var listName by remember { mutableStateOf("") }
    var isListNameEmpty by remember { mutableStateOf(false) }

    var textFieldColors by remember {
        mutableStateOf(Color.White)
    }

    val context = LocalContext.current

    HalfDialogBox(
        onDismiss = onDismiss
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Text(
                text = "Add new Task List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp ))


            TextField(
                value = listName,
                onValueChange = {
                    listName = it
                    isListNameEmpty = it.isEmpty()
                },
                singleLine = true,
                label = { Text("Name") },
                placeholder = { Text("Enter name") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = textFieldColors,
                    unfocusedIndicatorColor = textFieldColors,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(16.dp ))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if(!isListNameEmpty){
                        listName = viewModel.cleanString(listName)

                        val taskList: TaskList = TaskList(
                            listName = listName,
                        )

                        viewModel.addTaskList(taskList)
                        onDismiss()
                        Toast.makeText(context, "New Task List Added Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        textFieldColors = Color.Red
                        Toast.makeText(context, "Please enter valid Title", Toast.LENGTH_SHORT).show()
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
}