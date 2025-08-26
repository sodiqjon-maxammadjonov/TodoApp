package com.sdk.todoapp

//noinspection SuspiciousImport
import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat

@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Add")
            }
        }

        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index, item ->
                        TodoItem(item)
                    }
                }
            )
        }
    }
}

@Composable
fun TodoItem(item: Todo) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat(
                    "HH:mm:aa, dd/mm",
                    java.util.Locale.ENGLISH
                ).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_menu_delete),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}