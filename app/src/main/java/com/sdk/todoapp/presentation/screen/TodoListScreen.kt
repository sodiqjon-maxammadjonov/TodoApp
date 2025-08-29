package com.sdk.todoapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.presentation.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val todos = viewModel.displayedTodos
    val searchQuery = viewModel.searchQuery
    val selectedTodo = viewModel.selectedTodo
    val isEditing = viewModel.isEditing

    Scaffold(
        topBar = {
            Text(
                "Todo App",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Search Bar
            var searchText by remember { mutableStateOf(searchQuery) }
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    val it = null
                    searchText = it.toString()
                    viewModel.searchTodos(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Qidirish...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // Edit Dialog
            if (isEditing) {
                EditTodoDialog(
                    todo = selectedTodo,
                    onUpdate = viewModel::updateSelectedTodo,
                    onDismiss = viewModel::clearSelection
                )
            }

            var newTodoTitle by remember { mutableStateOf("") }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTodoTitle,
                    onValueChange = { newTodoTitle = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Yangi vazifa...") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newTodoTitle.isNotBlank()) {
                            viewModel.addNewTodo(newTodoTitle)
                            newTodoTitle = ""
                        }
                    }
                ) {
                    Text("Qo'shish")
                }
            }

            // Todos List
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onEdit = { viewModel.selectTodo(todo) },
                        onDelete = { viewModel.deleteTodo(todo.id!!) },
                        onToggleComplete = { viewModel.toggleTodoCompletion(todo.id!!) }
                    )
                }
            }

            // Bottom Counter
            Text(
                text = "Jami: ${todos.size} ta",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onEdit: () -> Unit, onDelete: () -> Unit, onToggleComplete: () -> Unit) {
    TODO("Not yet implemented")
}