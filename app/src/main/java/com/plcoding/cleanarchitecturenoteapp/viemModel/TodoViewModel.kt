package com.plcoding.cleanarchitecturenoteapp.viemModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.data.Todo
import com.plcoding.cleanarchitecturenoteapp.data.TodoRepository
import com.plcoding.cleanarchitecturenoteapp.routes.Routes
import com.plcoding.cleanarchitecturenoteapp.utils.TodoListEvent
import com.plcoding.cleanarchitecturenoteapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepo: TodoRepository) : ViewModel() {


    val todos = todoRepo.getTodos()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deleteTodo: Todo? = null


    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnUndoDeleteClickTodo -> {
                deleteTodo?.let { todo: Todo ->
                    viewModelScope.launch {
                        todoRepo.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    todoRepo.insertTodo(event.todo.copy(isDone = event.isDone))
                }
            }
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    deleteTodo = event.todo
                    todoRepo.deleteTodo(event.todo)
                    sendUiEvent(UIEvent.ShowSnackBar(message = "Todo deleted ", action = "Undo"))
                }
            }
        }

    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}