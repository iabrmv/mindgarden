package com.iabrmv.mindmaps.data.business

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.data.database.NodeEntity
import java.util.*

data class Node(
    var id: String = UUID.randomUUID().toString(),
    var text: String = "New node",
    var offset: Offset = Offset(100f, 100f),
    var rank: Int = 0 // distance from root node
): BusinessModel<NodeEntity> {
    override fun toEntity(): NodeEntity = NodeEntity(
        id = id,
        text = text,
        offset = offset,
        rank = rank
    )
}