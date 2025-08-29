package com.sdk.todoapp.data.local.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdk.todoapp.data.local.dao.TodoDao
import com.sdk.todoapp.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract class todoDao() : TodoDao
}