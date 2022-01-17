package com.iabrmv.mindmaps.business

import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.model.Edge
import com.iabrmv.mindmaps.model.EdgeStyle
import com.iabrmv.mindmaps.model.Mindmap
import com.iabrmv.mindmaps.model.Node
import io.realm.Realm
import io.realm.kotlin.where

// TODO realm
class MindmapManager(val realm: Realm) {
    var currentMindmap: Mindmap? = null

    fun selectMindmap(id: Int) {
        val list =  realm.where<Mindmap>().findAll()
        currentMindmap = realm.where<Mindmap>().equalTo("id", id).findFirst()
    }


    fun loadMindmaps(): List<Mindmap> = realm.where<Mindmap>().findAll()

    fun addMindmap(name: String) {
        val mindmap = Mindmap(name = name)
        realm.executeTransaction {
            it.copyToRealmOrUpdate(mindmap)
            currentMindmap = mindmap
        }
    }

    private fun addEdge(
        startIndex: Int,
        endIndex: Int,
        style: EdgeStyle? = null
    ) {
        currentMindmap?.run {
            edges.add(Edge(startIndex, endIndex, style))
        }
    }

    fun saveMindmap(mindmap: Mindmap) {

    }

    fun moveNode(nodeIndex: Int, delta: Offset) {
        realm.executeTransaction {
            currentMindmap?.run {
                nodes[nodeIndex]?.let {
                    it.xOffset += delta.x
                    it.yOffset += delta.y
                }
            }
        }
    }

    fun addNode(parentIndex: Int, node: Node) {
        realm.executeTransaction {
            currentMindmap?.run {
                nodes.add(node)
                addEdge(parentIndex, nodes.lastIndex)
            }
        }
    }

    fun removeNode(index: Int) {
        realm.executeTransaction {
            currentMindmap?.run {
                edges.removeAll { index == it.startIndex || index == it.endIndex }
                nodes.removeAt(index)
            }
        }
    }

    fun removeEdge(index: Int) {
        realm.executeTransaction {
            currentMindmap?.edges?.removeAt(index)
        }
    }

    fun updateNodeText(index: Int, text: String) {
        realm.executeTransaction {
            currentMindmap?.run {
                nodes[index]?.text = text
            }
        }
    }
}