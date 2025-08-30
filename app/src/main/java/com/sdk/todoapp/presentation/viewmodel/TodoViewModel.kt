package com.sdk.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.usecase.AddTodo
import com.sdk.todoapp.domain.usecase.DeleteTodo
import com.sdk.todoapp.domain.usecase.GetAllTodos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getAllTodos: GetAllTodos,
    private val addTodo: AddTodo,
    private val deleteTodo: DeleteTodo,
    private val updateTodo: UpdateTodo
) : ViewModel() {

    // Private state holders
    private val _allTodos = MutableStateFlow<List<Todo>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    // Public exposed states
    val searchQuery = _searchQuery.asStateFlow()
    val isLoading = _isLoading.asStateFlow()
    val error = _error.asStateFlow()

    // Combined displayed todos based on search
    val displayedTodos: StateFlow<List<Todo>> = combine(
        _allTodos,
        _searchQuery
    ) { todos, query ->
        if (query.isBlank()) {
            todos.sortedByDescending { it.createdAt }
        } else {
            todos.filter { todo ->
                todo.title.contains(query, ignoreCase = true) ||
                        todo.description.contains(query, ignoreCase = true)
            }.sortedByDescending { it.createdAt }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val todos = getAllTodos()
                _allTodos.value = todos

            } catch (e: Exception) {
                _error.value = e.message ?: "Ma'lumotlarni yuklashda xatolik yuz berdi"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNewTodo(title: String, description: String = "") {
        if (title.isBlank()) return

        viewModelScope.launch {
            try {
                _error.value = null

                val newTodo = Todo(
                    title = title.trim(),
                    description = description.trim(),
                    createdAt = System.currentTimeMillis(),
                    isCompleted = false
                )

                addTodo(newTodo)
                loadTodos() // Reload to get the updated list

            } catch (e: Exception) {
                _error.value = e.message ?: "Vazifa qo'shishda xatolik yuz berdi"
            }
        }
    }

    fun deleteTodoById(id: Int) {
        viewModelScope.launch {
            try {
                _error.value = null
                deleteTodo(id)
                loadTodos() // Reload to get the updated list

            } catch (e: Exception) {
                _error.value = e.message ?: "Vazifani o'chirishda xatolik yuz berdi"
            }
        }
    }

    fun toggleTodoComplete(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                _error.value = null

                val currentTodos = _allTodos.value
                val todoToUpdate = currentTodos.find { it.id == id } ?: return@launch

                val updatedTodo = todoToUpdate.copy(isCompleted = isCompleted)
                updateTodo(updatedTodo)
                loadTodos() // Reload to get the updated list

            } catch (e: Exception) {
                _error.value = e.message ?: "Vazifa holatini o'zgartirishda xatolik yuz berdi"
            }
        }
    }

    fun searchTodos(query: String) {
        _searchQuery.value = query.trim()
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun clearError() {
        _error.value = null
    }

    // Additional utility functions
    fun getCompletedTodosCount(): StateFlow<Int> {
        return _allTodos.map { todos ->
            todos.count { it.isCompleted }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    fun getPendingTodosCount(): StateFlow<Int> {
        return _allTodos.map { todos ->
            todos.count { !it.isCompleted }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    fun markAllAsCompleted() {
        viewModelScope.launch {
            try {
                _error.value = null
                val incompleteTodos = _allTodos.value.filter { !it.isCompleted }

                incompleteTodos.forEach { todo ->
                    updateTodo(todo.copy(isCompleted = true))
                }

                loadTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Barcha vazifalarni belgilashda xatolik yuz berdi"
            }
        }
    }

    fun deleteCompletedTodos() {
        viewModelScope.launch {
            try {
                _error.value = null
                val completedTodos = _allTodos.value.filter { it.isCompleted }

                completedTodos.forEach { todo ->
                    todo.id?.let { deleteTodo(it) }
                }

                loadTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Bajarilgan vazifalarni o'chirishda xatolik yuz berdi"
            }
        }
    }
}