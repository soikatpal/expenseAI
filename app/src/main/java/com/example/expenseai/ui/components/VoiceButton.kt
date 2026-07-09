package com.example.expenseai.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VoiceButton(
    onClick: () -> Unit
) {

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(80.dp)
    ) {

        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = "Voice Input"
        )

    }

}