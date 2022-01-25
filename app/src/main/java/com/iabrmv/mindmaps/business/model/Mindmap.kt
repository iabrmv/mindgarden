package com.iabrmv.mindmaps.business.model

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.database.EdgeEntity
import com.iabrmv.mindmaps.database.MindmapEntity
import com.iabrmv.mindmaps.database.NodeEntity
import io.realm.RealmList
import java.util.*

data class Mindmap(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "Unknown",
    var nodes: MutableList<Node> = mutableListOf(),
    var edges: MutableList<Edge> = mutableListOf()
): BusinessModel<MindmapEntity> {
    override fun toEntity(): MindmapEntity = MindmapEntity(
        id = id,
        name = name,
        nodes = RealmList<NodeEntity>().apply {
             nodes.forEach { add(it.toEntity()) }
        },
        edges = RealmList<EdgeEntity>().apply {
            edges.forEach { add(it.toEntity()) }
        }
    )

    fun addNode(parentIndex: Int) {
        val node = Node(offset = nodes[parentIndex].offset)
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

}
