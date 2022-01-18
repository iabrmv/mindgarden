package com.iabrmv.mindmaps.database

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.Node
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NodeEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var text: String = "New node",
    var xOffset: Float = 100f,
    var yOffset: Float = 100f,
    var style: NodeStyleEntity? = null
): RealmObject(), Entity<Node> {

    constructor(
        text: String,
        offset: Offset,
        style: NodeStyleEntity?
    ) : this(text = text, xOffset = offset.x, yOffset = offset.y, style = style)

    var offset
        get() = Offset(xOffset, yOffset)
        set(value) {
            xOffset = value.x
            yOffset = value.y
        }

    override fun toBusinessModel(): Node = Node(
        text = text,
        offset = offset,
        style = style?.toBusinessModel()
    )


}
