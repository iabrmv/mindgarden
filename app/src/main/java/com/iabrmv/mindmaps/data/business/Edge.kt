package com.iabrmv.mindmaps.data.business

import com.iabrmv.mindmaps.data.database.EdgeEntity
import java.util.*

data class Edge(
    var id: String = UUID.randomUUID().toString(),
    var startIndex: Int = 0,
    var endIndex: Int = 0
) : BusinessModel<EdgeEntity> {
    override fun toEntity(): EdgeEntity = EdgeEntity(
        id = id,
        startIndex = startIndex,
        endIndex = endIndex
    )
}