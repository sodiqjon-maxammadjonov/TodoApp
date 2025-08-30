package com.sdk.todoapp.di

import android.content.Context
import androidx.room.Room
import com.sdk.todoapp.data.local.dao.TodoDao
import com.sdk.todoapp.data.local.datbase.AppDatabase
import com.sdk.todoapp.data.repository.TodoRepositoryImpl
import com.sdk.todoapp.domain.repository.TodoRepository
import com.sdk.todoapp.domain.usecase.AddTodo
import com.sdk.todoapp.domain.usecase.DeleteTodo
import com.sdk.todoapp.domain.usecase.GetAllTodos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: AppDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetAllTodos(repository: TodoRepository): GetAllTodos {
        return GetAllTodos(repository)
    }

    @Provides
    @Singleton
    fun provideAddTodo(repository: TodoRepository): AddTodo {
        return AddTodo(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTodo(repository: TodoRepository): DeleteTodo {
        return DeleteTodo(repository)
    }
}