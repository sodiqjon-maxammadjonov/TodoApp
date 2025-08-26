package com.sdk.todoapp

import java.time.Instant
import java.util.Date

data class Todo(
    var id: Int,
    var title: String,
    var createdAt: Date
)

fun getFaceTodo() : List<Todo> {
    return  listOf<Todo>(
        Todo(1, "First todo", Date.from(Instant.now())),
        Todo(1, "Second todo", Date.from(Instant.now())),
        Todo(1, "Will bw third todo", Date.from(Instant.now())),
        Todo(1, "Fourth todo infrom psjifbeivtr vreijbitub", Date.from(Instant.now())),
    )
}