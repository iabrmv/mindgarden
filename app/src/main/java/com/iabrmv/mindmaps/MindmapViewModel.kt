package com.iabrmv.mindmaps

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel

class MindMapViewModel: ViewModel() {
    var nodes = mutableStateListOf("My goal")
    var offsets = mutableStateListOf(Offset(700f, 800f))
    var adjacencyMatrix by mutableStateOf(listOf(listOf(false)))

    fun onDrag(index: Int, dragAmount: Offset) {
        offsets[index] = Offset(
            offsets[index].x + dragAmount.x,
            offsets[index].y + dragAmount.y,
        )
    }

    fun onAdd(index: Int) {
        adjacencyMatrix = List(nodes.size + 1) { i ->
            if (i< nodes.size) {
                (adjacencyMatrix[i] + (index == i))
            } else {
                List(nodes.size + 1) { j ->
                    index == j
                }
            }
        }
        nodes.add("New node")
        offsets.add(offsets[index] + Offset(200f, 200f))
    }

    fun onTextChange(index: Int, newText: String) {
        nodes[index] = newText
    }

    fun onDelete(index: Int) {
        if(index != 0) {
            adjacencyMatrix = adjacencyMatrix.removeAt(index).map {
                it.removeAt(index)
            }

            nodes.removeAt(index)
            offsets.removeAt(index)
        }
    }

    private fun <T > List<T>.removeAt(index: Int): List<T> {
        return subList(0, index) + subList(index + 1, size)
    }
}