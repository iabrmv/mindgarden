package com.iabrmv.mindmaps.business.model

import com.iabrmv.mindmaps.database.EdgeStyleEntity
import java.util.*

data class EdgeStyle(
    var id: String = UUID.randomUUID().toString(),
    var animated: Boolean = false
) : BusinessModel<EdgeStyleEntity> {
    override fun toEntity(): EdgeStyleEntity = EdgeStyleEntity(id= id, animated = animated)

}
