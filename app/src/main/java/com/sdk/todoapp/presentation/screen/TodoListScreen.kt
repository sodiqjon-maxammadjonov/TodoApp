package com.sdk.todoapp.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sdk.todoapp.presentation.component.CustomBackground
import com.sdk.todoapp.presentation.screen.components.TodoItem
import com.sdk.todoapp.presentation.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val todos = viewModel.displayedTodos
    var searchQuery by remember { mutableStateOf(viewModel.searchQuery) }
    var newTodo by remember { mutableStateOf("") }

    CustomBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchTodos(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Qidirish...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Todo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTodo,
                    onValueChange = { newTodo = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Yangi vazifa...") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newTodo.isNotBlank()) {
                            viewModel.addNewTodo(newTodo)
                            newTodo = ""
                        }
                    }
                ) {
                    Text("Qo'shish")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Todo List
            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isNotEmpty())
                            "Hech narsa topilmadi"
                        else
                            "Hozircha vazifalar yo'q",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(todos) { todo ->
                        TodoItem(
                            todo = todo,
                            onDelete = { viewModel.deleteTodoById(todo.id!!) }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}