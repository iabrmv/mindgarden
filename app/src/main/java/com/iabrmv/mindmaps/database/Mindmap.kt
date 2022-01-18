package com.iabrmv.mindmaps.database

import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.Mindmap
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MindmapEntity(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "Unknown",
    var nodes: RealmList<NodeEntity> = RealmList(),
    var edges: RealmList<EdgeEntity> = RealmList()
): RealmObject(), Entity<Mindmap> {

    override fun toBusinessModel(): Mindmap = Mindmap(
        name = name,
        nodes = nodes.map { it.toBusinessModel() }.toMutableList(),
        edges = edges.map { it.toBusinessModel() }.toMutableList()
    )

}