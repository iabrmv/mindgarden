package com.iabrmv.mindmaps.ui.mindmap

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.round
import com.iabrmv.mindmaps.data.business.Edge
import com.iabrmv.mindmaps.data.business.Node

@Composable
fun Mindmap(
    name: String,
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
    Column {
        Header(text = name, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        )
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
                Node(
                    text = node.text,
                    rank = node.rank,
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
}
