package com.iabrmv.mindmaps.database

import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.EdgeStyle
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class EdgeStyleEntity(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var animated: Boolean = false
): RealmObject(), Entity<EdgeStyle> {
    override fun toBusinessModel(): EdgeStyle = EdgeStyle(
        id = id,
        animated = animated
    )
}