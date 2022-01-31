package com.plcoding.cleanarchitecturenoteapp.utils

import com.plcoding.cleanarchitecturenoteapp.data.Todo

sealed class TodoListEvent {

    data class OnDeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo,val isDone:Boolean) : TodoListEvent()
    object OnUndoDeleteClickTodo : TodoListEvent()
    object OnAddTodoClick : TodoListEvent()

}