package com.sdk.todoapp.data.local.datbase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdk.todoapp.data.local.dao.TodoDao
import com.sdk.todoapp.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}