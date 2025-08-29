package com.sdk.todoapp.domain.usecase

import com.sdk.todoapp.domain.repository.TodoRepository

class DeleteTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteTodo(id)
}