package com.iabrmv.mindmaps.database

import androidx.compose.ui.graphics.Color
import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.NodeStyle
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NodeStyleEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var backgroundColor: Long = 0xFFFFFFFF,
    var borderColor: Long = 0xFFFFFFFF,
    var animated: Boolean = false
): RealmObject(), Entity<NodeStyle> {
    override fun toBusinessModel(): NodeStyle = NodeStyle(
        id = id,
        backgroundColor = Color(backgroundColor),
        borderColor = Color(borderColor),
        animated = animated
    )

}