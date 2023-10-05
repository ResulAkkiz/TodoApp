package com.project.todoapp.data.datasource

import com.project.todoapp.data.entity.Todo
import com.project.todoapp.room.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoDataSource(private var todoDao: TodoDao) {

    suspend fun insertTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.insertTodo(todo)
        }
    }

    suspend fun updateTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }
    }

    suspend fun deleteTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.deleteTodo(todo)
        }
    }

    suspend fun getTodoList(): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext todoDao.getTodoList()
    }

    suspend fun searchTodo(query: String): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext todoDao.searchTodo(query)
    }


}