package com.iabrmv.mindmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.ExperimentalComposeUiApi
import com.iabrmv.mindmaps.ui.mindmap.Mindmap
import com.iabrmv.mindmaps.ui.routing.Destination
import com.iabrmv.mindmaps.ui.saves.SavesScreen
import com.iabrmv.mindmaps.ui.theme.MindMapsTheme
import com.iabrmv.mindmaps.viewModel.MindGardenViewModel

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MindGardenViewModel by viewModels()
        setContent {
            MindMapsTheme {
                with(viewModel) {
                    when(currentDestination) {
                        Destination.Mindmap -> Mindmap(
                            nodes = nodes,
                            edges = edges,
                            lastTouchedNodeIndex = lastTouchedNodeIndex,
                            hasFocus = isNodeTextFocused,
                            onSetFocus = ::setFocus,
                            onClearFocus = ::clearFocus,
                            onTouchNode = ::selectNode,
                            onDrag = ::move,
                            onReleaseDrag = ::confirmMove,
                            onAddNode = ::addNode,
                            onRemoveNode = ::removeNode,
                            onRemoveEdge = ::removeEdge,
                            onTextChange = ::updateText,
                            onDoneEdit = ::stopEditingText,
                            onExit = ::exitMindmapScreen
                        )
                        Destination.Saves -> SavesScreen(
                            names = mindmaps,
                            onMindmapClick = ::onSelectMindmap,
                            onAddMindmap = ::addMindmap
                        )
                    }
                }
            }
        }
    }
}
