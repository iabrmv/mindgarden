package com.iabrmv.mindmaps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NodeAlternative(
    text: String,
    modifier: Modifier = Modifier,
    onAdd: () -> Unit = { },
    onTextChange: (String) -> Unit = { },
    onDelete: () -> Unit = { },
    onStyleChangeIntent: () -> Unit = { }
) {
    var showMenu by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val textFieldValue = TextFieldValue(text, TextRange(text.length))
    val scope = rememberCoroutineScope()

    DisposableEffect(enabled, showMenu) {
        onDispose {
            if(enabled && !showMenu) {
                scope.launch {
                    delay(100)
                    focusRequester.requestFocus()
                }
            }
        }
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { onTextChange(it.text) },
            enabled = enabled,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    enabled = false
                }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .requiredWidth(IntrinsicSize.Min)
                .focusRequester(focusRequester)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        showMenu = true
                    })
                }
                .background(Color.White, shape = CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .padding(16.dp),
            textStyle = TextStyle(fontSize = 24.sp)
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            val buttons = mapOf(
                "Rename" to { enabled = true },
                "Delete" to { onDelete() },
                "New node" to { onAdd() },
                "Style" to { onStyleChangeIntent() }
            )

            buttons.forEach {
                DropdownMenuItem(onClick = {
                    showMenu = false
                    it.value()
                }) {
                    Text(it.key)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun NodeAlternativePreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        NodeAlternative("My goal")
    }
}