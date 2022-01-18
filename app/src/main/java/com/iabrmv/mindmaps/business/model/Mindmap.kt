package com.iabrmv.mindmaps.business.model

import com.iabrmv.mindmaps.database.EdgeEntity
import com.iabrmv.mindmaps.database.MindmapEntity
import com.iabrmv.mindmaps.database.NodeEntity
import io.realm.RealmList

data class Mindmap(
    var name: String = "Unknown",
    var nodes: MutableList<Node> = mutableListOf(),
    var edges: MutableList<Edge> = mutableListOf()
): BusinessModel<MindmapEntity> {
    override fun toEntity(): MindmapEntity = MindmapEntity(
        name = name,
        nodes = RealmList<NodeEntity>().apply {
             nodes.forEach { add(it.toEntity()) }
        },
        edges = RealmList<EdgeEntity>().apply {
            edges.forEach { add(it.toEntity()) }
        }
    )
}
