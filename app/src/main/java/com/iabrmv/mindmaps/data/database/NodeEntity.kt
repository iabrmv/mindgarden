package com.iabrmv.mindmaps.data.database

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.data.business.Node
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NodeEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var text: String = "New node",
    var xOffset: Float = 100f,
    var yOffset: Float = 100f,
    var rank: Int = 0
): RealmObject(), Entity<Node> {

    constructor(
        id: String,
        text: String,
        offset: Offset,
        rank: Int
    ) : this(id = id, text = text, xOffset = offset.x, yOffset = offset.y, rank = rank)

    var offset
        get() = Offset(xOffset, yOffset)
        set(value) {
            xOffset = value.x
            yOffset = value.y
        }

    override fun toBusinessModel(): Node = Node(
        id = id,
        text = text,
        offset = offset,
        rank = rank
    )
}
