package com.iabrmv.mindmaps.data.database

import com.iabrmv.mindmaps.data.business.Edge
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class EdgeEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var startIndex: Int = 0,
    var endIndex: Int = 0
): RealmObject(), Entity<Edge> {
    override fun toBusinessModel(): Edge = Edge(
        id = id,
        startIndex = startIndex,
        endIndex = endIndex
    )
}