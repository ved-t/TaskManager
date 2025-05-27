package com.example.taskmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onConfirm: (TimePickerState) ->Unit,
    onDismiss: () -> Unit
){
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pick Time",
            )
            TimePicker(state = timePickerState)
            Row (

            ){
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        onConfirm(timePickerState)
                        onDismiss()
                    }
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}