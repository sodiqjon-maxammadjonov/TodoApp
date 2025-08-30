package com.sdk.todoapp.presentation.screen.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TodoStatusIcon(
    isCompleted: Boolean,
    onToggle: (Boolean) -> Unit,
    size: Dp = 32.dp,
    iconSize: Dp = 24.dp
) {
    IconButton(
        onClick = { onToggle(!isCompleted) },
        modifier = Modifier.size(size)
    ) {
        Icon(
            imageVector = if (isCompleted) {
                Icons.Filled.CheckCircle
            } else {
                Icons.Outlined.Build
            },
            contentDescription = if (isCompleted) "Bajarilgan" else "Bajarilmagan",
            tint = if (isCompleted) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.size(iconSize)
        )
    }
}