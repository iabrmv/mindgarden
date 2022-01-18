package com.iabrmv.mindmaps.database

import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.EdgeStyle
import io.realm.RealmObject

open class EdgeStyleEntity(
    var animated: Boolean = false
): RealmObject(), Entity<EdgeStyle> {
    override fun toBusinessModel(): EdgeStyle = EdgeStyle(
        animated = animated
    )

}