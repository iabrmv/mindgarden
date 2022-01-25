package com.iabrmv.mindmaps.business.model

import com.iabrmv.mindmaps.database.EdgeEntity
import java.util.*

data class Edge(
    var id: String = UUID.randomUUID().toString(),
    var startIndex: Int = 0,
    var endIndex: Int = 0,
    var style: EdgeStyle? = null
) : BusinessModel<EdgeEntity> {
    override fun toEntity(): EdgeEntity = EdgeEntity(
        id = id,
        startIndex = startIndex,
        endIndex = endIndex,
        style = style?.toEntity()
    )
}