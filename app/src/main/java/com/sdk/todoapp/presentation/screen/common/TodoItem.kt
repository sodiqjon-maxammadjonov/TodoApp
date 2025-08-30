package com.sdk.todoapp.presentation.screen.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.presentation.component.common.ConfirmationDialog

@Composable
fun TodoItem(
    todo: Todo,
    onDelete: () -> Unit,
    onToggleComplete: (Boolean) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isCompleted) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (todo.isCompleted) 2.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TodoStatusIcon(
                isCompleted = todo.isCompleted,
                onToggle = onToggleComplete
            )

            Spacer(modifier = Modifier.width(12.dp))

            TodoContent(
                todo = todo,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            DeleteButton(
                onClick = { showDeleteDialog = true }
            )
        }

        CompletionIndicator(isCompleted = todo.isCompleted)
    }

    ConfirmationDialog(
        isVisible = showDeleteDialog,
        title = "Vazifani o'chirish",
        message = "\"${todo.title}\" vazifasini o'chirishni xohlaysizmi? Bu amalni bekor qilib bo'lmaydi.",
        confirmButtonText = "O'chirish",
        dismissButtonText = "Bekor qilish",
        onConfirm = onDelete,
        onDismiss = { showDeleteDialog = false },
        isDestructive = true
    )
}

