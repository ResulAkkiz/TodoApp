package com.project.todoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.project.todoapp.data.entity.Todo
import com.project.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KayitFragmentViewModel @Inject constructor(private var todoRepository: TodoRepository) :
    ViewModel() {
    fun insertTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            todoRepository.insertTodo(todo)
        }
    }
}