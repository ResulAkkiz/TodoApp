package com.project.todoapp.data.repository
import com.project.todoapp.data.datasource.TodoDataSource
import com.project.todoapp.data.entity.Todo

class TodoRepository(private var todoDataSource: TodoDataSource) {

    suspend fun insertTodo(todo: Todo) =todoDataSource.insertTodo(todo)

    suspend fun updateTodo(todo: Todo)=todoDataSource.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) =todoDataSource.deleteTodo(todo)

    suspend fun getTodoList(): List<Todo> = todoDataSource.getTodoList()

    suspend fun searchTodo(query: String): List<Todo> = todoDataSource.searchTodo(query)

}