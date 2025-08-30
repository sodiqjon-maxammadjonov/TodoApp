package com.sdk.todoapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
            .focusRequester(focusRequester)
    ) {
        content()
    }
}