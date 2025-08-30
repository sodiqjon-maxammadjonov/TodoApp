package com.sdk.todoapp.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdk.todoapp.domain.model.Todo

@Composable
fun TodoItem(todo: Todo, onDelete: () -> Unit) {
    Card(Modifier.padding(8.dp)) {
        Row(Modifier.padding(16.dp)) {
            Text(
                text = todo.title,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Delete")
            }
        }
    }
}