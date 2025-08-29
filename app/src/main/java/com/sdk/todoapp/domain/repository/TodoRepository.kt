package com.sdk.todoapp.domain.repository

import com.sdk.todoapp.domain.model.Todo

interface TodoRepository {
    suspend fun getAllTodos(): List<Todo>
    suspend fun addTodo(todo: Todo)
    suspend fun deleteTodo(id: Int)
}