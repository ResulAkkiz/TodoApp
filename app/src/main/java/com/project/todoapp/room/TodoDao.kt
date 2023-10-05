package com.project.todoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.todoapp.data.entity.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodoList(): List<Todo>

    @Query("SELECT * FROM Todo WHERE todo_title like '%' || :query ||'%'")
    fun searchTodo(query: String): List<Todo>

    @Insert
    fun insertTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}