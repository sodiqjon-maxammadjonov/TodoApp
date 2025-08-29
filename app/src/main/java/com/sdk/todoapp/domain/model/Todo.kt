package com.sdk.todoapp.domain.model

data class Todo(
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)