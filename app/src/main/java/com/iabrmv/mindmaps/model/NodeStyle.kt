package com.iabrmv.mindmaps.model

import androidx.compose.ui.graphics.Color
import io.realm.RealmObject

open class NodeStyle(
    var backgroundColor: Long = 0xFFFFFFFF,
    var borderColor: Long = 0xFFFFFFFF,
    var animated: Boolean = false
): RealmObject()