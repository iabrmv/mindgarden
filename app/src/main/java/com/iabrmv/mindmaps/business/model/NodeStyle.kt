package com.iabrmv.mindmaps.business.model

import androidx.compose.ui.graphics.Color
import com.iabrmv.mindmaps.database.NodeStyleEntity
import java.util.*

data class NodeStyle(
    var id: String = UUID.randomUUID().toString(),
    var backgroundColor: Color,
    var borderColor: Color,
    var animated: Boolean = false
): BusinessModel<NodeStyleEntity?> {
    override fun toEntity(): NodeStyleEntity = NodeStyleEntity(
        id = id,
        backgroundColor = backgroundColor.value.toLong(),
        borderColor = borderColor.value.toLong(),
        animated = animated
    )
}
