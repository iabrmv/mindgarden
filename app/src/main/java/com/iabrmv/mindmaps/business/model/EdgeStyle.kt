package com.iabrmv.mindmaps.business.model

import com.iabrmv.mindmaps.database.EdgeStyleEntity

data class EdgeStyle(
    var animated: Boolean = false
) : BusinessModel<EdgeStyleEntity> {
    override fun toEntity(): EdgeStyleEntity = EdgeStyleEntity(animated)

}
