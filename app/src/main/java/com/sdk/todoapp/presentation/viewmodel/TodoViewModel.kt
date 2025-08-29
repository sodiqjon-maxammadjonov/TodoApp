package com.sdk.todoapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.usecase.AddTodo
import com.sdk.todoapp.domain.usecase.DeleteTodo
import com.sdk.todoapp.domain.usecase.GetAllTodos
import com.sdk.todoapp.domain.usecase.GetTodoById
import com.sdk.todoapp.domain.usecase.ToggleTodoCompletion
import com.sdk.todoapp.domain.usecase.UpdateTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodos: GetAllTodos,
    private val getTodoById: GetTodoById,
    private val addTodo: AddTodo,
    private val updateTodo: UpdateTodo,
    private val deleteTodo: DeleteTodo,
    private val toggleTodoCompletion: ToggleTodoCompletion
) : ViewModel() {

    private val _allTodos = mutableStateListOf<Todo>()
    private val _displayedTodos = mutableStateListOf<Todo>()
    val displayedTodos: List<Todo> get() = _displayedTodos

    private val _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    private val _selectedTodo = mutableStateOf<Todo?>(null)
    val selectedTodo: Todo? get() = _selectedTodo.value

    private val _isEditing = mutableStateOf(false)
    val isEditing: Boolean get() = _isEditing.value

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

    fun addNewTodo(title: String, description: String = "") {
        viewModelScope.launch {
            val newTodo = Todo(title = title, description = description)
            addTodo(newTodo)
            loadTodos()
        }
    }

    fun updateSelectedTodo(title: String, description: String = "") {
        viewModelScope.launch {
            _selectedTodo.value?.let { todo ->
                val updatedTodo = todo.copy(title = title, description = description)
                updateTodo(updatedTodo)
                loadTodos()
                clearSelection()
            }
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            deleteTodo(id)
            loadTodos()
        }
    }

    fun toggleTodoCompletion(id: Int) {
        viewModelScope.launch {
            toggleTodoCompletion(id)
            loadTodos()
        }
    }

    fun searchTodos(query: Nothing?) {
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
                todo.title.lowercase().contains(query) ||
                        todo.description.lowercase().contains(query)
            })
        }
    }

    fun selectTodo(todo: Todo) {
        _selectedTodo.value = todo
        _isEditing.value = true
    }

    fun clearSelection() {
        _selectedTodo.value = null
        _isEditing.value = false
    }

    suspend fun getTodoById(id: Int): Todo? {
        return getTodoById(id)
    }
}