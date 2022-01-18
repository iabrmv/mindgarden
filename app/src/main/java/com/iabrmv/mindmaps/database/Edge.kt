package com.iabrmv.mindmaps.database

import com.iabrmv.mindmaps.business.model.Edge
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class EdgeEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var startIndex: Int = 0,
    var endIndex: Int = 0,
    var style: EdgeStyleEntity? = null
): RealmObject(), Entity<Edge> {
    override fun toBusinessModel(): Edge = Edge(
        startIndex = startIndex,
        endIndex = endIndex,
        style = style?.toBusinessModel()
    )

}