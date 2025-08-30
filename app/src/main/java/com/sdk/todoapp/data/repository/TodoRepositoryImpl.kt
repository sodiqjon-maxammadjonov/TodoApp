package com.sdk.todoapp.data.repository

import com.sdk.todoapp.data.local.dao.TodoDao
import com.sdk.todoapp.data.local.entity.toEntity
import com.sdk.todoapp.data.local.entity.toTodo
import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.repository.TodoRepository
import jakarta.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun getAllTodos(): List<Todo> {
        return todoDao.getAllTodos().map { it.toTodo() }
    }

    override suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(id: Int) {
        todoDao.getAllTodos().find { it.id == id }?.let { todoDao.deleteTodo(it.id!!) }
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }
}