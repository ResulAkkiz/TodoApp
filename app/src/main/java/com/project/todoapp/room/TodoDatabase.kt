package com.project.todoapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.todoapp.data.entity.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(PriorityConverter::class)
abstract class TodoDatabase :RoomDatabase() {
    abstract fun getTodoDao() : TodoDao
}