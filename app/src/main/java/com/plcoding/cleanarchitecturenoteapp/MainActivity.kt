package com.plcoding.cleanarchitecturenoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.routes.Routes
import com.plcoding.cleanarchitecturenoteapp.screens.AddEditTodoScreen
import com.plcoding.cleanarchitecturenoteapp.screens.TodoListScreen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {

                    composable(Routes.TODO_LIST) {
                        TodoListScreen(onNavigate = { nextRoute ->
                            navController.navigate(nextRoute.route)
                        })
                    }
                    composable(
                        Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }

                }
            }
        }
    }
}
