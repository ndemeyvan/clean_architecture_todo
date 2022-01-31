package com.plcoding.cleanarchitecturenoteapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cleanarchitecturenoteapp.data.Todo
import com.plcoding.cleanarchitecturenoteapp.utils.TodoListEvent


@Composable
fun TodoItem(todo: Todo, onEvent: (TodoListEvent) -> Unit, modifier: Modifier = Modifier) {


    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = todo.title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onEvent(TodoListEvent.OnDeleteTodo(todo))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Todo Icon")
                }
            }
            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold))

            }

        }

        Checkbox(checked = todo.isDone, onCheckedChange = { isChecked ->
            onEvent(TodoListEvent.OnDoneChange(todo = todo, isDone = isChecked))
        })

    }


}