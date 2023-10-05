package com.project.todoapp.di

import android.content.Context
import androidx.room.Room
import com.project.todoapp.data.datasource.TodoDataSource
import com.project.todoapp.data.repository.TodoRepository
import com.project.todoapp.room.TodoDao
import com.project.todoapp.room.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTodoRepository(todoDataSource: TodoDataSource): TodoRepository {
        return TodoRepository(todoDataSource)
    }

    @Provides
    @Singleton
    fun provideTodoDataSource(todoDao: TodoDao): TodoDataSource {
        return TodoDataSource(todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoDao(@ApplicationContext context: Context): TodoDao {
        val  todoDatabase = Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "TodoDatabase.sqlite"
        ).createFromAsset("TodoDatabase.sqlite").build()
        return todoDatabase.getTodoDao()
    }
}