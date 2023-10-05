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
class DetayFragmentViewModel @Inject constructor( var todoRepository: TodoRepository) :
    ViewModel() {

    fun updateTodo(todo: Todo) {
        CoroutineScope(Dispatchers.Main).launch {
            todoRepository.updateTodo(todo)
        }
    }
}