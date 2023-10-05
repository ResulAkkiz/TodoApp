package com.project.todoapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.todoapp.data.entity.Todo
import com.project.todoapp.data.repository.TodoRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaFragmentViewModel @Inject constructor( var todoRepository: TodoRepository) :
    ViewModel() {
    val todoList = MutableLiveData<List<Todo>>()

    init {
        getTodoList()
    }


    fun deleteTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            todoRepository.deleteTodo(todo)
            getTodoList()
        }
    }

    fun getTodoList() {
        CoroutineScope(Dispatchers.Main).launch {
            val data = todoRepository.getTodoList()
            todoList.value = data
        }
    }

    fun updateTodo(todo:Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            todoRepository.updateTodo(todo)
            getTodoList()
        }
    }

    fun searchTodo(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val data = todoRepository.searchTodo(query)
            todoList.value = data
        }
    }


}