package com.iabrmv.mindmaps.ui.mindmap

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.iabrmv.mindmaps.data.business.Edge
import com.iabrmv.mindmaps.data.business.Node
import com.iabrmv.mindmaps.viewModel.MindGardenViewModel.Companion.TAG
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
    var showBin by remember { mutableStateOf(false) }
    var pointerInputChange: PointerInputChange? = null
    BackHandler {
        onExit()
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        val appropriateScale =
            with(LocalDensity.current) {
            getAppropriateScale(maxWidth.toPx(), maxHeight.toPx())
        }
        val size = with(LocalDensity.current) {
            Offset(maxWidth.toPx() , maxHeight.toPx())
        }
        val initialOffset = Offset.Zero //size * .5f - gravityCenter
        var scale by remember { mutableStateOf(appropriateScale) }
        var offset by remember { mutableStateOf(initialOffset * appropriateScale) }
        val binOffset = Offset(size.x / 2, size.y  - 200f)
        var alpha by remember { mutableStateOf(.5f)}
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
                .scale(scale)
                .offset { offset.round() }
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
                            coroutineScope {
                                launch {
                                    detectTapGestures {
                                        onTouchNode(i)
                                        Log.d(TAG, "Set focus for node $i")
                                        onSetFocus()
                                    }
                                }
                                launch {
                                    detectDragGestures(
                                        onDragStart = {
                                            onTouchNode(i)
                                            Log.d(TAG, "Start dragging node $i")
                                            showBin = true
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consumeAllChanges()
                                            onDrag(i, dragAmount)
                                            pointerInputChange = change
                                        },
                                        onDragEnd = {
                                            onReleaseDrag()
                                            Log.d(TAG, "dragged change = $pointerInputChange")
                                            val position = pointerInputChange?.position ?: Offset.Zero
                                            val originalPosition = Offset(
                                                position.x / scale + offset.x,
                                                position.y / scale + offset.y
                                            )
                                            val dist = (originalPosition + nodes[i].offset - binOffset).getDistance()
                                            Log.d(TAG, "distance = $dist")
                                            if(dist < 200f / scale) {
                                                Log.d(TAG, "REMOVE")
                                                onRemoveNode()
                                            }
                                            showBin = false
                                        }
                                    )
                                }
                                launch {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = {
                                            onTouchNode(i)
                                            onAddNode()
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consumeAllChanges()
                                            onDrag(nodes.lastIndex, dragAmount)
                                        },
                                        onDragEnd = onReleaseDrag
                                    )
                                }
                            }
                        },
                    onAdd = onAddNode,
                    onDelete = onRemoveNode,
                    onTextChange = onTextChange,
                    onDoneEdit = onDoneEdit
                )
            }
        }
        if(showBin) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colors.primary.copy(alpha = alpha),
                modifier = Modifier
                    .size(64.dp)
                    .align(CenterElementAlignment)
                    .offset { binOffset.round() }
            )
        }
    }
}

