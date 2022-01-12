package com.iabrmv.mindmaps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// TODO customize style
@Composable
fun Node(
    text: String,
    modifier: Modifier = Modifier,
    onAdd: () -> Unit = { },
    onTextChange: (String) -> Unit = { }
) {
    Box(modifier = modifier.padding(16.dp)) {
        Box {
            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .requiredWidth(IntrinsicSize.Min)
                    .background(Color.White, shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 24.sp)
            )
        }
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .size(48.dp),
        ) {
            IconButton(
                onClick = onAdd,
                modifier = Modifier.align { size, space, layoutDirection ->
                    IntOffset(
                        0,
                        size.height / 2
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colors.background, CircleShape)

                )
            }
        }
    }
}

@Preview
@Composable
fun NodePreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        Node("My goal")
    }
}