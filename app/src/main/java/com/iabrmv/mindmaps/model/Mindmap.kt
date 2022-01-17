package com.iabrmv.mindmaps.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Mindmap(
    @PrimaryKey var id: Int = 0,
    var name: String = "Unknown",
    var nodes: RealmList<Node> = RealmList(Node()),
    var edges: RealmList<Edge> = RealmList()
): RealmObject()