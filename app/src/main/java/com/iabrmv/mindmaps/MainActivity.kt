package com.iabrmv.mindmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.ExperimentalComposeUiApi
import com.iabrmv.mindmaps.ui.MindMap
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
                        Destination.Mindmap -> MindMap(
                            nodes = texts,
                            offsets = offsets,
                            edges = edges,
                            focusedIndex = focusedIndex,
                            onFocusChange = ::setFocus,
                            clearFocus = ::clearFocus,
                            onDrag = ::onDrag,
                            onAdd = ::onAdd,
                            onDelete = ::onRemoveNode,
                            onTextChange = ::onTextChange,
                            onExit = ::onExit
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
