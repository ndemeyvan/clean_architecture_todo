package com.plcoding.cleanarchitecturenoteapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.cleanarchitecturenoteapp.viemModel.TodoViewModel
import com.plcoding.cleanarchitecturenoteapp.components.TodoItem
import com.plcoding.cleanarchitecturenoteapp.utils.TodoListEvent
import com.plcoding.cleanarchitecturenoteapp.utils.UIEvent
import kotlinx.coroutines.flow.collect


@Composable
fun TodoListScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    // S'execute sans prendre en compte le cycle de vie d'un composable
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClickTodo)
                    }
                }
                is UIEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }

    }
//    Scaffold
    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(TodoListEvent.OnAddTodoClick) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo Icon ")

        }
    },) {

        LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White)) {
            items(todos.value) { todo ->
                TodoItem(todo = todo, onEvent = viewModel::onEvent, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                    }
                    .padding(16.dp)
                )

            }
        }

    }


}