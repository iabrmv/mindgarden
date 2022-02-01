package com.iabrmv.mindmaps.viewModel

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.iabrmv.mindmaps.business.MindmapManager
import com.iabrmv.mindmaps.business.model.Mindmap
import com.iabrmv.mindmaps.business.model.Node
import com.iabrmv.mindmaps.ui.routing.Destination
import io.realm.Realm

class MindGardenViewModel: ViewModel() {
    private val manager = MindmapManager(Realm.getDefaultInstance())
    var currentDestination by mutableStateOf(Destination.Saves)

    // Saves Screen
    var mindmaps = mutableStateListOf<Mindmap>()
    var mindmap: Mindmap? by mutableStateOf(null, referentialEqualityPolicy())

    // Mindmap screen
    var lastTouchedNodeIndex: Int? by mutableStateOf(null)
    var isNodeTextFocused: Boolean by mutableStateOf(false)

    init {
        mindmaps.addAll(manager.loadMindmaps())
    }

    fun onAddMindmap(name: String) {
        mindmaps.add(Mindmap(name))
        mindmap = mindmaps[mindmaps.lastIndex]
        saveMindmap()
        currentDestination = Destination.Mindmap
    }

    fun onSelectMindmap(index: Int) {
        mindmap = mindmaps[index]
        currentDestination = Destination.Mindmap
    }

    fun onAddNode() = runOnTouchedNode { index ->
        updateMindmap { it.addNode(index) }
        saveMindmap()
    }

    fun onRemoveNode() {
        runOnTouchedNode { index ->
            updateMindmap { it.removeNode(index) }
            saveMindmap()
        }
        clearLastTouchedIndex()
    }

    fun onTouchNode(index: Int) {
        lastTouchedNodeIndex = index
    }

    fun onDrag(index: Int, delta: Offset) = updateMindmap { it.moveNode(index, delta) }

    fun onMoveDone() = saveMindmap()

    fun onSetFocus() {
        isNodeTextFocused = true
    }

    fun onEditText(newText: String) = runOnFocusedNode { index ->
        updateMindmap { it.updateNodeText(index, newText) }
    }

    fun onDoneEditText() = runOnTouchedNode {
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

    private fun updateMindmap(change: (Mindmap) -> Unit) {
        mindmap = mindmap?.copy()?.also {
            change(it)
        }
    }

    private fun runOnTouchedNode(block: (Int) -> Unit) =
        lastTouchedNodeIndex?.run { block(this) }

    private fun runOnFocusedNode(block: (Int) -> Unit) =
        runOnTouchedNode { if(isNodeTextFocused) block(it) }


    private fun saveMindmap() = mindmap?.let { manager.saveMindmap(it) }

    private fun clearLastTouchedIndex() {
        lastTouchedNodeIndex = null
    }

    private fun clearFocus() {
        isNodeTextFocused = false
    }
}