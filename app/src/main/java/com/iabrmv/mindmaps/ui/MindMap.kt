package com.iabrmv.mindmaps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.round

@Composable
fun MindMap(
    nodes: List<String>,
    offsets: List<Offset>,
    onDrag: (Int, Offset) -> Unit,
    onAdd: (Int) -> Unit = { },
    onDelete: (Int) -> Unit = { },
    onTextChange: (Int, String) -> Unit = { _, _ -> },
    incidenceMatrix: List<List<Boolean>>
) {
    val nodesCount = nodes.size
    val arrowColor = MaterialTheme.colors.primary

    Box(Modifier.fillMaxSize()) {
        for(i in 0 until nodesCount - 1) {
            for(j in i + 1 until nodesCount) {
                if(incidenceMatrix[i][j]) {
                    Edge(
                        color = arrowColor,
                        modifier = Modifier,
                        start = offsets[i],
                        end = offsets[j]
                    )
                }
            }
        }
        for(i in 0 until nodesCount) {
            NodeAlternative(
                text = nodes[i],
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
                onTextChange = { newText ->
                    onTextChange(i, newText)
                }
            )
        }
    }

}

@Preview
@Composable
fun MindMapPreview() {
    val nodes = listOf("Things to do", "More things to do", "GOAL", "Workaround")
    val offsets = listOf(
        Offset(100f, 50f),
        Offset(300f, 200f),
        Offset(104f, 300f),
        Offset(200f, 500f)
    ).toMutableStateList()


    val incidenceMatrix = listOf(
        listOf(false, true, true, false),
        listOf(true, false, true, false),
        listOf(true, true, false, true),
        listOf(false, false, true, false)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        MindMap(
            nodes = nodes,
            offsets = offsets,
            onDrag = { index, dragAmount ->
                offsets[index] = Offset(
                    offsets[index].x + dragAmount.x,
                    offsets[index].y + dragAmount.y,
                )
            },
            incidenceMatrix = incidenceMatrix
        )
    }
}