package com.sdk.todoapp.domain.usecase

import com.sdk.todoapp.domain.model.Todo
import com.sdk.todoapp.domain.repository.TodoRepository

class GetAllTodos(private val repository: TodoRepository) {
    suspend operator fun invoke(): List<Todo> = repository.getAllTodos()
}

class GetTodoById(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): Todo? = repository.getTodoById(id)
}

class AddTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo): Long = repository.addTodo(todo)
}

class UpdateTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) = repository.updateTodo(todo)
}

class DeleteTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteTodo(id)
}

class ToggleTodoCompletion(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) = repository.toggleTodoCompletion(id)
}
