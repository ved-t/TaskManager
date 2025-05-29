package com.example.taskmanager.presentation.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.data.local.Repeat
import com.example.taskmanager.presentation.ui.common.FullScreenDialogBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatTaskDialog(
    onDismiss: () -> Unit,
){
    FullScreenDialogBox(
        onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(20.dp)
        ) {
            Text(
                text = "Repeat",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(16.dp))

            var numberText by remember { mutableStateOf("1") }
            var selectedRepeatFrequency by remember { mutableStateOf(Repeat.None) }

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                OutlinedTextField(
                    value = numberText,
                    onValueChange = {numberText = it},
                    label = {Text(text = "Times")},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                )

                Text(
                    text = "Times",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                )

                var repeatExpanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = repeatExpanded,
                    onExpandedChange = {repeatExpanded = !repeatExpanded},
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
                            .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                    )
                    ExposedDropdownMenu(
                        expanded = repeatExpanded,
                        onDismissRequest = {repeatExpanded = false}
                    ) {
                        Repeat.entries.forEach { value ->
                            if(value.value != null){
                                DropdownMenuItem(
                                    text = { Text(value.name) },
                                    onClick = {
                                        selectedRepeatFrequency = value
                                        repeatExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when(selectedRepeatFrequency){
                Repeat.Daily -> {
                    DisplayDailyContent()
                }
                Repeat.Weekly -> {
                    DisplayWeeklyContent()
                }
                Repeat.Monthly -> {
                    DisplayMonthlyContent()
                }
                else -> {}
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onDismiss) {
                    Text("Confirm Repeat")
                }
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun DisplayDailyContent(){

}


@Composable
fun DisplayWeeklyContent(){

}


@Composable
fun DisplayMonthlyContent(){

}

