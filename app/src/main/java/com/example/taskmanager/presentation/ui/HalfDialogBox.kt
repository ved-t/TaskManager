package com.example.taskmanager.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun HalfDialogBox(
    onDismiss: ()->Unit,
    utilityFunction: @Composable () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        utilityFunction()
    }
}