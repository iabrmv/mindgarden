package com.iabrmv.mindmaps.database

import androidx.compose.ui.graphics.Color
import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.NodeStyle
import io.realm.RealmObject

open class NodeStyleEntity(
    var backgroundColor: Long = 0xFFFFFFFF,
    var borderColor: Long = 0xFFFFFFFF,
    var animated: Boolean = false
): RealmObject(), Entity<NodeStyle> {
    override fun toBusinessModel(): NodeStyle = NodeStyle(
        backgroundColor = Color(backgroundColor),
        borderColor = Color(borderColor),
        animated = animated
    )

}