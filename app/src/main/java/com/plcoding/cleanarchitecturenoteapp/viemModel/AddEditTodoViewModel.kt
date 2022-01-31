package com.plcoding.cleanarchitecturenoteapp.viemModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.data.Todo
import com.plcoding.cleanarchitecturenoteapp.data.TodoRepository
import com.plcoding.cleanarchitecturenoteapp.utils.AddEditTodoEvent
import com.plcoding.cleanarchitecturenoteapp.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val todoRepo: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                todoRepo.getTodoById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditTodoViewModel.todo = todo
                }
            }

        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(UIEvent.ShowSnackBar(message = "Title can't be empty"))
                        return@launch
                    }
                    todoRepo.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUiEvent(UIEvent.PopBackStack)
                }
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description

            }
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title

            }
        }

    }


    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
