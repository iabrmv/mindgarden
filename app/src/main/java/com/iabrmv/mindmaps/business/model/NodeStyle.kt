package com.iabrmv.mindmaps.business.model

import androidx.compose.ui.graphics.Color
import com.iabrmv.mindmaps.database.NodeStyleEntity

data class NodeStyle(
    var backgroundColor: Color,
    var borderColor: Color,
    var animated: Boolean = false
): BusinessModel<NodeStyleEntity?> {
    override fun toEntity(): NodeStyleEntity = NodeStyleEntity(
        backgroundColor = backgroundColor.value.toLong(),
        borderColor = borderColor.value.toLong(),
        animated = animated
    )
}
