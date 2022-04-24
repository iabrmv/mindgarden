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
import com.iabrmv.mindmaps.util.TimeUtils.toDateString
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
                        Destination.Mindmap -> mindmap?.run {
                            Mindmap(
                                name = name,
                                nodes = nodes,
                                edges = edges,
                                gravityCenter = gravityCenter,
                                getAppropriateScale = ::getAppropriateScale,
                                lastTouchedNodeIndex = lastTouchedNodeIndex,
                                hasFocus = isNodeTextFocused,
                                onSetFocus = ::onSetFocus,
                                onClearFocus = ::onDismiss,
                                onTouchNode = ::onTouchNode,
                                onDrag = ::onDrag,
                                onReleaseDrag = ::onMoveDone,
                                onAddNode = ::onAddNode,
                                onRemoveNode = ::onRemoveNode,
                                onRemoveEdge = { },
                                onTextChange = ::onEditText,
                                onDoneEdit = ::onDoneEditText,
                                onExit = ::onBackPressed
                            )
                        }
                        Destination.Saves -> SavesScreen(
                            names = mindmaps.map { it.name },
                            dates = mindmaps.map {
                               "Edited ${it.lastEditedTimeMillis.toDateString()}"
                            },
                            onMindmapClick = ::onSelectMindmap,
                            onAddMindmap = ::onAddMindmap
                        )
                    }
                }
            }
        }
    }
}
