package com.iabrmv.mindmaps.data.business

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.data.database.EdgeEntity
import com.iabrmv.mindmaps.data.database.MindmapEntity
import com.iabrmv.mindmaps.data.database.NodeEntity
import io.realm.RealmList
import java.util.*

data class Mindmap(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "Unknown",
    var nodes: MutableList<Node> = mutableListOf(),
    var edges: MutableList<Edge> = mutableListOf(),
    var createdTimeMillis: Long = Calendar.getInstance().timeInMillis,
    var lastEditedTimeMillis: Long = Calendar.getInstance().timeInMillis
): BusinessModel<MindmapEntity> {
    override fun toEntity(): MindmapEntity = MindmapEntity(
        id = id,
        name = name,
        nodes = RealmList<NodeEntity>().apply {
             nodes.forEach { add(it.toEntity()) }
        },
        edges = RealmList<EdgeEntity>().apply {
            edges.forEach { add(it.toEntity()) }
        },
        createdTimeMillis = createdTimeMillis,
        lastEditedTimeMillis = lastEditedTimeMillis
    )

    constructor(name: String, rootOffset: Offset) : this(
        name = name,
        nodes = mutableListOf(
            Node(text = name, offset = rootOffset)
        )
    )

    fun addNode(parentIndex: Int) {
        val node = Node(
            offset = nodes[parentIndex].offset + Offset(100f, 100f),
            rank = nodes[parentIndex].rank + 1
        )
        nodes.add(node)
        edges.add(Edge(startIndex = parentIndex, endIndex = nodes.lastIndex))
    }

    fun updateNodeText(nodeIndex: Int, newText: String) {
        nodes[nodeIndex].text = newText
    }

    fun removeNode(index: Int) {
        nodes.removeAt(index)
        edges.removeAll {
            it.startIndex == index || it.endIndex == index
        }
    }

    fun moveNode(index: Int, delta: Offset) {
        nodes[index].offset += delta
    }

    fun updateEditTime() {
        lastEditedTimeMillis = Calendar.getInstance().timeInMillis
    }

}
