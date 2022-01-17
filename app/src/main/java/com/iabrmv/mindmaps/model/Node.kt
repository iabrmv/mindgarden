package com.iabrmv.mindmaps.model

import androidx.compose.ui.geometry.Offset
import io.realm.RealmObject

open class Node(
    var text: String = "New node",
    var xOffset: Float = 0f,
    var yOffset: Float = 100f,
    var style: NodeStyle? = null
): RealmObject() {
    val offset
    get() = Offset(xOffset, yOffset)
}
