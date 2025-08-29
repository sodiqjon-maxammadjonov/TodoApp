package com.sdk.todoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.todoapp.domain.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)


fun TodoEntity.toTodo() = Todo(id, title, description, createdAt, isCompleted)
fun Todo.toEntity() = TodoEntity(id, title, description, createdAt, isCompleted)