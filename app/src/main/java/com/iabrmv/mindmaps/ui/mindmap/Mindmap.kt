package com.iabrmv.mindmaps.ui.mindmap

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.Node

@Composable
fun Mindmap(
    nodes: List<Node>,
    edges: List<Edge>,
    lastTouchedNodeIndex: Int?,
    hasFocus: Boolean,
    onSetFocus: () -> Unit,
    onClearFocus: () -> Unit,
    onDrag: (Int, Offset) -> Unit,
    onTouchNode: (Int) -> Unit,
    onReleaseDrag: () -> Unit,
    onAddNode: () -> Unit = { },
    onRemoveNode: () -> Unit = { },
    onAddEdge: (Int, Int) -> Unit = { _, _ -> },
    onRemoveEdge: (Int) -> Unit = { },
    onTextChange: (String) -> Unit = { },
    onDoneEdit: () -> Unit = { },
    onExit: () -> Unit = { }
) {
    val arrowColor = MaterialTheme.colors.primary

    BackHandler {
        onExit()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClearFocus() }
    ) {
        edges.forEach {
            Edge(
                color = arrowColor,
                modifier = Modifier,
                start = nodes[it.startIndex].offset,
                end = nodes[it.endIndex].offset
            )
        }
        nodes.forEachIndexed { i, node ->
            NodeAlternative(
                text = node.text,
                isFocused = lastTouchedNodeIndex == i && hasFocus,
                onReceiveFocus = { onSetFocus() },
                modifier = Modifier
                    .offset { node.offset.round() }
                    .align(CenterElementAlignment)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { onTouchNode(i) },
                            onDrag = { change, dragAmount ->
                                change.consumeAllChanges()
                                onDrag(i, dragAmount)
                            },
                            onDragEnd = onReleaseDrag
                        )
                    },
                onTouch = { onTouchNode(i) },
                onAdd = onAddNode,
                onDelete = onRemoveNode,
                onTextChange = onTextChange,
                onDoneEdit = onDoneEdit
            )
        }
    }
}
