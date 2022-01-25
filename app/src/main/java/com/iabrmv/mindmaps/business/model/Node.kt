package com.iabrmv.mindmaps.business.model

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.database.NodeEntity
import java.util.*

data class Node(
    var id: String = UUID.randomUUID().toString(),
    var text: String = "New node",
    var offset: Offset = Offset(100f, 100f),
    var style: NodeStyle? = null
): BusinessModel<NodeEntity> {
    override fun toEntity(): NodeEntity = NodeEntity(
        id = id,
        text = text,
        offset = offset,
        style = style?.toEntity()
    )
}