package com.iabrmv.mindmaps.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.iabrmv.mindmaps.business.MindmapManager
import com.iabrmv.mindmaps.business.model.Edge
import com.iabrmv.mindmaps.business.model.Mindmap
import com.iabrmv.mindmaps.business.model.Node
import com.iabrmv.mindmaps.ui.routing.Destination
import io.realm.Realm

class MindmapsViewModel: ViewModel() {
    private val manager = MindmapManager(Realm.getDefaultInstance())
    var currentDestination by mutableStateOf(Destination.Saves)

    init {
        //manager.clear()
    }
    // Saves Screen
    var mindmaps = manager.loadMindmaps().map { it.name }.toMutableStateList()

    fun addMindmap(name: String) {
        mindmap = Mindmap(
            name = name,
            nodes = mutableListOf(Node("My Goal"))
        ).also {
            manager.addMindmap(it)
            mindmaps.add(it.name)
        }
        selectedMindmapIndex = mindmaps.lastIndex
        currentDestination = Destination.Mindmap
    }

    var selectedMindmapIndex: Int? by mutableStateOf(null)

    fun onSelectMindmap(index: Int) {
        selectedMindmapIndex = index
        mindmap = manager.loadMindmap(index) ?: Mindmap()
        currentDestination = Destination.Mindmap
    }

    // Mindmap Screen
    private var mindmap: Mindmap? = null
        set(value) {
            if(value != null) {
                field = value
                texts = value.nodes.map { node -> node.text }.toMutableStateList()
                offsets = value.nodes.map { node -> node.offset }.toMutableStateList()
                edges = value.edges.toMutableStateList()
            }
        }

    var lastTouchedNodeIndex: Int? by mutableStateOf(null)
    var isNodeTextFocused: Boolean by mutableStateOf(false)

    var texts = mutableStateListOf<String>()
    var offsets = mutableStateListOf<Offset>()
    var edges = mutableStateListOf<Edge>()


    fun clearFocus() {
        isNodeTextFocused = false
    }

    fun setFocus() {
        isNodeTextFocused = true
    }

    fun move(index: Int, delta: Offset) {
        offsets[index] += delta
    }

    fun confirmMove(index: Int) {
        save()
    }

    fun selectNode(index: Int) {
        lastTouchedNodeIndex = index
    }

    private fun clearLastTouchedIndex() {
        lastTouchedNodeIndex = null
    }

    fun addNode(index: Int) {
        val node = Node(offset = offsets[index] + Offset(0f, 100f))
        texts.add(node.text)
        offsets.add(node.offset)
        edges.add(Edge(startIndex = index, endIndex = texts.lastIndex))
    }

    fun updateText(newText: String) = lastTouchedNodeIndex?.let {
        if(isNodeTextFocused) {
            texts[it] = newText
        }
    }

    fun stopEditingText() =  lastTouchedNodeIndex?.let {
        save()
        clearFocus()
    }

    private fun save() = mindmap?.let { mindmap ->
        selectedMindmapIndex?.let { manager.saveMindmap( mindmap) }
    }

    fun removeNode() {
        lastTouchedNodeIndex?.let { index ->
            texts.removeAt(index)
            offsets.removeAt(index)
            edges.removeAll {
                it.startIndex == index || it.endIndex == index
            }
            save()
        }
        clearLastTouchedIndex()
    }

    fun removeEdge(index: Int) {
        edges.removeAt(index)
        save()
    }

    fun exitMindmapScreen() {
        save()
        currentDestination = Destination.Saves
    }
}