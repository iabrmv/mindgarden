package com.iabrmv.mindmaps.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import com.iabrmv.mindmaps.business.MindmapManager
import com.iabrmv.mindmaps.business.model.Mindmap
import com.iabrmv.mindmaps.business.model.Node
import com.iabrmv.mindmaps.ui.routing.Destination
import io.realm.Realm

class MindGardenViewModel {
    private val manager = MindmapManager(Realm.getDefaultInstance())
    private var currentDestination by mutableStateOf(Destination.Saves)

    // Saves Screen
    var mindmaps = mutableStateListOf<Mindmap>()
    var selectedMindmapIndex: Int? by mutableStateOf(null)
    val mindmap: Mindmap?
        get() = selectedMindmapIndex?.let { mindmaps[it] }

    // Mindmap screen
    var lastTouchedNodeIndex: Int? by mutableStateOf(null)
    var isNodeTextFocused: Boolean by mutableStateOf(false)

    init {
        mindmaps.addAll(manager.loadMindmaps())
        // manager.clear()
    }

    fun onAddMindmap(name: String) {
        mindmaps.add(Mindmap(name = name, nodes = mutableListOf(Node("My Goal"))))
        selectedMindmapIndex = mindmaps.lastIndex
        saveMindmap()
        currentDestination = Destination.Mindmap
    }

    fun onSelectMindmap(index: Int) {
        selectedMindmapIndex = index
        currentDestination = Destination.Mindmap
    }

    fun onAddNode() {
        lastTouchedNodeIndex?.let {
            mindmap?.addNode(it)
            saveMindmap()
        }
    }

    fun onRemoveNode() {
        lastTouchedNodeIndex?.let { index ->
            mindmap?.removeNode(index)
            saveMindmap()
        }
        clearLastTouchedIndex()
    }

    fun onTouchNode(index: Int) {
        lastTouchedNodeIndex = index
    }

    fun onDrag(delta: Offset) {
        lastTouchedNodeIndex?.let {
            mindmap?.moveNode(index = it, delta = delta)
        }
    }

    fun onMoveDone(index: Int) {
        saveMindmap()
    }

    fun setFocus() {
        isNodeTextFocused = true
    }

    fun onEditText(newText: String) = lastTouchedNodeIndex?.let {
        if(isNodeTextFocused) {
            mindmap?.updateNodeText(it, newText)
        }
    }

    fun onDoneEditText() = lastTouchedNodeIndex?.let {
        saveMindmap()
        clearFocus()
    }

    fun onDismiss() {
        clearFocus()
    }


    fun onBackPressed() {
        saveMindmap()
        currentDestination = Destination.Saves
    }


    private fun saveMindmap() = selectedMindmapIndex?.let {
        manager.saveMindmap(mindmaps[it])
    }

    private fun clearLastTouchedIndex() {
        lastTouchedNodeIndex = null
    }

    private fun clearFocus() {
        isNodeTextFocused = false
    }
}