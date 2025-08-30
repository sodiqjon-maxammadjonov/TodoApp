package com.sdk.todoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.usecase.AddTodo
import com.sdk.todoapp.domain.usecase.DeleteTodo
import com.sdk.todoapp.domain.usecase.GetAllTodos
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodos: GetAllTodos,
    private val addTodo: AddTodo,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    private val _allTodos = mutableStateListOf<Todo>()
    private val _displayedTodos = mutableStateListOf<Todo>()
    val displayedTodos: List<Todo> get() = _displayedTodos

    private val _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            _allTodos.clear()
            _allTodos.addAll(getAllTodos())
            applySearchFilter()
        }
    }

    fun addNewTodo(title: String) {
        viewModelScope.launch {
            addTodo(Todo(title = title))
            loadTodos()
        }
    }

    fun deleteTodoById(id: Int) {
        viewModelScope.launch {
            deleteTodo(id)
            loadTodos()
        }
    }

    fun searchTodos(query: String) {
        _searchQuery.value = query
        applySearchFilter()
    }

    private fun applySearchFilter() {
        if (_searchQuery.value.isEmpty()) {
            _displayedTodos.clear()
            _displayedTodos.addAll(_allTodos)
        } else {
            val query = _searchQuery.value.lowercase()
            _displayedTodos.clear()
            _displayedTodos.addAll(_allTodos.filter { todo ->
                todo.title.lowercase().contains(query)
            })
        }
    }
}