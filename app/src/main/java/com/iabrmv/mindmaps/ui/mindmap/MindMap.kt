package com.iabrmv.mindmaps.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.round
import com.iabrmv.mindmaps.model.Edge

@Composable
fun MindMap(
    nodes: List<String>,
    offsets: List<Offset>,
    edges: List<Edge>,
    focusedIndex: Int?,
    onFocusChange: (Int) -> Unit,
    clearFocus: () -> Unit,
    onDrag: (Int, Offset) -> Unit,
    onAdd: (Int) -> Unit = { },
    onDelete: (Int) -> Unit = { },
    onTextChange: (String) -> Unit = { },
    onExit: () -> Unit = { }
) {
    val arrowColor = MaterialTheme.colors.primary

    BackHandler {
        onExit()
    }

    Box(Modifier.fillMaxSize()) {
        edges.forEach {
            Edge(
                color = arrowColor,
                modifier = Modifier,
                start = offsets[it.startIndex],
                end = offsets[it.endIndex]
            )
        }
        nodes.forEachIndexed { i, node ->
            NodeAlternative(
                text = node,
                isFocused = focusedIndex == i,
                onReceiveFocus = { onFocusChange(i) },
                onDismiss = clearFocus,
                modifier = Modifier
                    .offset { offsets[i].round() }
                    .align(CenterElementAlignment)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consumeAllChanges()
                            onDrag(i, dragAmount)
                        }
                    },
                onAdd = { onAdd(i) },
                onDelete = { onDelete(i) },
                onTextChange = onTextChange
            )
        }
    }
}
