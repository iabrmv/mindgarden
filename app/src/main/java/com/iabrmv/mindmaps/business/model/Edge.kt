package com.iabrmv.mindmaps.business.model

import com.iabrmv.mindmaps.database.EdgeEntity

data class Edge(
    var startIndex: Int = 0,
    var endIndex: Int = 0,
    var style: EdgeStyle? = null
) : BusinessModel<EdgeEntity> {
    override fun toEntity(): EdgeEntity = EdgeEntity(
        startIndex = startIndex,
        endIndex = endIndex,
        style = style?.toEntity()
    )
}