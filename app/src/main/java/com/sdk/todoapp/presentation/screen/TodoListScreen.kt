package com.sdk.todoapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sdk.todoapp.presentation.component.EmptyState
import com.sdk.todoapp.presentation.component.common.SearchField
import com.sdk.todoapp.presentation.screen.common.*
import com.sdk.todoapp.presentation.viewmodel.TodoViewModel
import com.sdk.todoapp.ui.utils.clearFocusOnTap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val todos by viewModel.displayedTodos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var newTodo by remember { mutableStateOf("") }

    LaunchedEffect(searchQuery) {
        viewModel.searchTodos(searchQuery)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Vazifalar",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clearFocusOnTap() // Faqat shu yerda ishlatamiz
                .navigationBarsPadding()
                .imePadding()
        ) {
            // Header section with search and add
            HeaderSection(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                newTodo = newTodo,
                onNewTodoChange = { newTodo = it },
                onAddTodo = {
                    if (newTodo.isNotBlank()) {
                        viewModel.addNewTodo(newTodo.trim())
                        newTodo = ""
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Content section
            ContentSection(
                todos = todos,
                isSearching = searchQuery.isNotEmpty(),
                onDeleteTodo = { todoId ->
                    viewModel.deleteTodoById(todoId)
                },
                onToggleComplete = { todoId, isCompleted ->
                    viewModel.toggleTodoComplete(todoId, isCompleted)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun HeaderSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    newTodo: String,
    onNewTodoChange: (String) -> Unit,
    onAddTodo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        SearchField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = "Vazifalarni qidirish..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddTodoField(
            value = newTodo,
            onValueChange = onNewTodoChange,
            onAddClick = onAddTodo,
            placeholder = "Yangi vazifa kiriting..."
        )
    }
}

@Composable
private fun ContentSection(
    todos: List<com.sdk.todoapp.domain.model.Todo>,
    isSearching: Boolean,
    onDeleteTodo: (Int) -> Unit,
    onToggleComplete: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (todos.isEmpty()) {
        EmptyState(
            isSearching = isSearching,
            modifier = modifier
        )
    } else {
        TodoList(
            todos = todos,
            onDeleteTodo = onDeleteTodo,
            onToggleComplete = onToggleComplete,
            modifier = modifier
        )
    }
}

@Composable
private fun TodoList(
    todos: List<com.sdk.todoapp.domain.model.Todo>,
    onDeleteTodo: (Int) -> Unit,
    onToggleComplete: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = todos,
            key = { it.id ?: 0 }
        ) { todo ->
            TodoItem(
                todo = todo,
                onDelete = {
                    todo.id?.let(onDeleteTodo)
                },
                onToggleComplete = { isCompleted ->
                    todo.id?.let { id ->
                        onToggleComplete(id, isCompleted)
                    }
                }
            )
        }
    }
}