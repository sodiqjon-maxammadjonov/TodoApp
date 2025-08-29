package com.sdk.todoapp.domain.repository

import com.sdk.todoapp.domain.model.Todo

interface TodoRepository {
    suspend fun getAllTodos(): List<Todo>
    suspend fun getTodoById(id: Int): Todo?
    suspend fun addTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(id: Int)
    suspend fun toggleTodoCompletion(id: Int)
}