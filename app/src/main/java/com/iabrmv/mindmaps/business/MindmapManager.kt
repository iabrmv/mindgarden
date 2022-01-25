package com.iabrmv.mindmaps.business

import com.iabrmv.mindmaps.business.model.Mindmap
import com.iabrmv.mindmaps.database.*
import io.realm.Realm
import io.realm.kotlin.where

class MindmapManager(val realm: Realm) {
    var currentMindmap: Mindmap? = null

    fun loadMindmap(id: Int) = realm
        .where<MindmapEntity>()
        .equalTo("id", id)
        .findFirst()?.toBusinessModel()

    fun loadMindmaps(): List<Mindmap> = realm
        .where<MindmapEntity>()
        .findAll()
        .map {
            it.toBusinessModel()
        }

    fun addMindmap(mindmap: Mindmap) {
        realm.executeTransaction { realm ->
            val mindmapEntity = mindmap.toEntity()
            realm.copyToRealm(mindmapEntity)
            currentMindmap = mindmap
        }
    }

    fun saveMindmap(mindmap: Mindmap) {
        realm.executeTransaction { realm ->
            var storedEntity = realm
                .where<MindmapEntity>()
                .equalTo("id", mindmap.id)
                .findFirst()
            if(storedEntity != null) {
                storedEntity = mindmap.toEntity()
                realm.copyToRealmOrUpdate(storedEntity)
            }
            else realm.cancelTransaction()
        }
    }

//
//    private fun addEdge(
//        startIndex: Int,
//        endIndex: Int,
//        style: EdgeStyle? = null
//    ) {
//        currentMindmap?.run {
//            edges.add(Edge(startIndex = startIndex, endIndex = endIndex, style = style))
//        }
//    }
//
//    private fun Realm.save() {
//        currentMindmap?.let {
//            copyToRealmOrUpdate(it)
//        }
//    }
//
//    fun moveNodeTo(nodeIndex: Int, newPoint: Offset) {
//        realm.executeTransaction { realm ->
//            currentMindmap?.run {
//                nodes[nodeIndex]?.let {
//                    it.xOffset = newPoint.x
//                    it.yOffset = newPoint.y
//                    realm.save()
//                }
//            }
//        }
//    }
//
//    fun addNode(parentIndex: Int, node: Node) {
//        realm.executeTransaction { realm ->
//            currentMindmap?.run {
//                realm.copyToRealmOrUpdate(node)
//                nodes.add(node)
//                addEdge(parentIndex, nodes.lastIndex)
//            }
//        }
//    }
//
//    fun removeNode(index: Int) {
//        realm.executeTransaction {
//            currentMindmap?.run {
//                edges.removeAll { index == it.startIndex || index == it.endIndex }
//                nodes.removeAt(index)
//            }
//        }
//    }
//
//    fun removeEdge(index: Int) {
//        realm.executeTransaction {
//            currentMindmap?.edges?.removeAt(index)
//        }
//    }
//
//    fun updateNodeText(index: Int, text: String) {
//        realm.executeTransaction {
//            currentMindmap?.run {
//                nodes[index]?.text = text
//            }
//        }
//    }

    fun clear() {
        // Delete all persons
        // Using executeTransaction with a lambda reduces code size and makes it impossible
        // to forget to commit the transaction.
        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
    }
}