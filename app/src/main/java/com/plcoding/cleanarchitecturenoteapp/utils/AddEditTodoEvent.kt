package com.plcoding.cleanarchitecturenoteapp.utils

sealed class AddEditTodoEvent {

    data class OnTitleChange(val title: String) : AddEditTodoEvent()
    data class OnDescriptionChange(val description: String) : AddEditTodoEvent()
    object OnSaveTodoClick : AddEditTodoEvent()

}
