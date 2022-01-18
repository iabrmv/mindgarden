package com.iabrmv.mindmaps.business.model

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.database.NodeEntity

data class Node(
    var text: String = "New node",
    var offset: Offset = Offset(100f, 100f),
    var style: NodeStyle? = null
): BusinessModel<NodeEntity> {
    override fun toEntity(): NodeEntity = NodeEntity(
        text = text,
        offset = offset,
        style = style?.toEntity()
    )
}