package com.iabrmv.mindmaps.model

import io.realm.RealmObject

open class Edge(
    var startIndex: Int = 0,
    var endIndex: Int = 0,
    var style: EdgeStyle? = null
): RealmObject()