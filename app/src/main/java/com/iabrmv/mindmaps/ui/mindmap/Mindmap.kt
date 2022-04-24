package com.iabrmv.mindmaps.ui.mindmap

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.iabrmv.mindmaps.data.business.Edge
import com.iabrmv.mindmaps.data.business.Node

@Composable
fun Mindmap(
    name: String,
    nodes: List<Node>,
    edges: List<Edge>,
    gravityCenter: Offset,
    getAppropriateScale: (width: Float, height: Float) -> Float,
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

    BackHandler {
        onExit()
    }
    Column {
        Header(text = name, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            val appropriateScale = with(LocalDensity.current) {
                getAppropriateScale(maxWidth.toPx(), maxHeight.toPx())
            }
            val initialOffset = with(LocalDensity.current) {
                Offset(maxWidth.toPx() / 2, maxHeight.toPx() / 2) - gravityCenter
            }
            var scale by remember { mutableStateOf(appropriateScale) }
            var offset by remember { mutableStateOf(initialOffset * appropriateScale) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rot ->
                            onClearFocus()
                            offset += pan
                            scale *= zoom
                        }
                    }
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
            ) {
                edges.forEach {
                    Box(Modifier.border(2.dp, Color.Black)) {
                        Edge(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier,
                            start = nodes[it.startIndex].offset,
                            end = nodes[it.endIndex].offset
                        )
                    }
                }
                nodes.forEachIndexed { i, node ->
                    Node(
                        text = node.text,
                        rank = node.rank,
                        isFocused = lastTouchedNodeIndex == i && hasFocus,
                        onReceiveFocus = { onSetFocus() },
                        modifier = Modifier
                            .offset { (node.offset).round() }
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
}
