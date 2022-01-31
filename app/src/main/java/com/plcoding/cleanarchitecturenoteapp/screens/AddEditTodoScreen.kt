package com.plcoding.cleanarchitecturenoteapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.cleanarchitecturenoteapp.utils.AddEditTodoEvent
import com.plcoding.cleanarchitecturenoteapp.utils.UIEvent
import com.plcoding.cleanarchitecturenoteapp.viemModel.AddEditTodoViewModel
import kotlinx.coroutines.flow.collect
import java.nio.file.WatchEvent


@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()

) {
    val scaffold = rememberScaffoldState()
    // S'execute sans prendre en compte le cycle de vie d'un composable
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    scaffold.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                    )
                }
                else -> Unit
            }
        }
    }

//    Scaffold
    Scaffold(scaffoldState = scaffold, modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp), floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
        }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Save or Update a todo")
        }
    }) {

        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            TextField(value = viewModel.title, onValueChange = { newTitleValue ->
                viewModel.onEvent(AddEditTodoEvent.OnTitleChange(newTitleValue))
            }, placeholder = {
                Text(text = "Please Enter The title")
            },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = { newTitleValue ->
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(newTitleValue))
                },
                placeholder = {
                    Text(text = "Please Enter The description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5,
            )
        }


    }


}