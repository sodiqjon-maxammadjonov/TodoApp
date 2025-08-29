package com.sdk.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sdk.todoapp.data.local.entity.TodoEntity

@Dao
interface TodoDao{
    @Query("SELECT * FROM todo")
    suspend fun getAllTodos(): List<TodoEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoEntity)
    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteTodo(id: TodoEntity)
    @Query("DELETE FROM todo")
    suspend fun deleteAllTodos()
    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?
    @Query("SELECT * FROM todo WHERE isCompleted = :isCompleted")
    suspend fun getCompletedTodos(isCompleted: Boolean = true): List<TodoEntity>
    @Query("SELECT * FROM todo WHERE isCompleted = :isCompleted")
    suspend fun getUncompletedTodos(isCompleted: Boolean = false): List<TodoEntity>
}