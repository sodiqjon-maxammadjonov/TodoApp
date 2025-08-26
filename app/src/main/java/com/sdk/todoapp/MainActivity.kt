package com.sdk.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdk.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.safeDrawing.asPaddingValues())
                        .consumeWindowInsets(WindowInsets.safeDrawing),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoListPage(todoViewModel)
                }
            }
        }
    }
}
