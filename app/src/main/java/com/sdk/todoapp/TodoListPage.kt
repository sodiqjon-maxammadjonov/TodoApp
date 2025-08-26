package com.sdk.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodoListPage (){
    val todoList = getFaceTodo();
    Column (
        modifier = Modifier.fillMaxSize()
            .padding(8.dp)
    ){
        LazyColumn (
            content = {
                itemsIndexed(todoList) { index, item ->
                    Text(item.toString())
                }
            }
        )
    }
}