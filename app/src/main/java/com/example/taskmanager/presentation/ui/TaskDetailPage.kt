package com.example.taskmanager.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.taskmanager.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailPage(
    onDismiss: () -> Unit,
    task: Task? = null,
    header: String,
    viewModel: TaskViewModel = hiltViewModel()
){
    var title by remember { mutableStateOf(task!!.title) }
    var description by remember { mutableStateOf(task!!.description) }

    val isTitleEmpty = remember{ mutableStateOf(true) }
    val isDescriptionEmpty = remember{ mutableStateOf(true) }

    var textFieldColors by remember {
        mutableStateOf(Color.White)
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = header,
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

        Spacer(modifier = Modifier.height(16.dp))

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
        var selectedOption by remember { mutableStateOf(task!!.priority) }

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

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if(title.isNotEmpty() && description.isNotEmpty()){
                    title = viewModel.cleanString(title)
                    description = viewModel.cleanString(description)

                    val updatedTask = task?.copy(title = title, description = description, priority = selectedOption)

                    if (updatedTask != null) {
                        viewModel.updateTask(updatedTask)
                    }
                    onDismiss()
                    Toast.makeText(context, "Task Updated Successfully", Toast.LENGTH_LONG).show()
                }
                else{
                    textFieldColors = Color.Red
                    Toast.makeText(context, "Please enter valid Title", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Update")
            }
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    }
}