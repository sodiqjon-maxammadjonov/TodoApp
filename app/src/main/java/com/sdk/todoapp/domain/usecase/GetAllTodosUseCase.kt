package com.sdk.todoapp.domain.usecase

import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.repository.TodoRepository

class GetAllTodos(private val repository: TodoRepository) {
    suspend operator fun invoke(): List<Todo> = repository.getAllTodos()
}