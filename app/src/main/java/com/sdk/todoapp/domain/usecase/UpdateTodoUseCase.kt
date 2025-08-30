package com.sdk.todoapp.domain.usecase

import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.repository.TodoRepository

class UpdateTodoUseCase (private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) = repository.updateTodo(todo)
}