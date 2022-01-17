package com.iabrmv.mindmaps.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.iabrmv.mindmaps.business.MindmapManager
import com.iabrmv.mindmaps.model.Edge
import com.iabrmv.mindmaps.model.Mindmap
import com.iabrmv.mindmaps.model.Node
import com.iabrmv.mindmaps.ui.routing.Destination
import io.realm.Realm

class MindGardenViewModel: ViewModel() {
    private val manager = MindmapManager(Realm.getDefaultInstance())
    var currentDestination by mutableStateOf(Destination.Saves)

    // Saves Screen
    var mindmaps = manager.loadMindmaps().map { it.name }.toMutableStateList()

    fun addMindmap(name: String) {
        manager.addMindmap(name)
        mindmaps.add(name)
        onSelectMindmap(mindmaps.lastIndex)
    }

    var selectedMindmapIndex: Int? by mutableStateOf(null)

    fun onSelectMindmap(index: Int) {
        selectedMindmapIndex = index
        manager.selectMindmap(index)
        updateFromDb()
        currentDestination = Destination.Mindmap
    }

    // Mindmap Screen

    private val mindmap get() = manager.currentMindmap

    var focusedIndex: Int? by mutableStateOf(null)

    var texts = mutableStateListOf<String>()
    var offsets = mutableStateListOf<Offset>()
    var edges = mutableStateListOf<Edge>()

    fun updateFromDb() {
        texts = (mindmap?.nodes?.map{ it.text} ?: listOf()).toMutableStateList()
        offsets = (mindmap?.nodes?.map{ it.offset} ?: listOf()).toMutableStateList()
        edges = (mindmap?.edges ?: listOf()).toMutableStateList()
    }


    fun clearFocus() {
        focusedIndex = null
    }

    fun setFocus(index: Int) {
        focusedIndex = index
    }

    fun onDrag(index: Int, dragAmount: Offset) {
        offsets[index] += dragAmount
        manager.moveNode(index, dragAmount)
    }

    fun onAdd(index: Int) {
        val node = Node()

        texts.add(node.text)
        offsets.add(node.offset)
        edges.add(Edge(index, texts.lastIndex))

        manager.addNode(index, node)
    }

    fun onTextChange(newText: String) = focusedIndex?.let {
        texts[it] = newText
        manager.updateNodeText(it, newText)
    }

    fun onRemoveNode(index: Int) {
        texts.removeAt(index)
        offsets.removeAt(index)
        edges.removeAll {
            it.startIndex == index || it.endIndex == index
        }
        manager.removeNode(index)
    }

    fun onRemoveEdge(index: Int) {
        edges.removeAt(index)
        manager.removeEdge(index)
    }

    fun onExit() {
        currentDestination = Destination.Saves
    }
}