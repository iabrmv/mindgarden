package com.iabrmv.mindmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.ExperimentalComposeUiApi
import com.iabrmv.mindmaps.ui.MindMap
import com.iabrmv.mindmaps.ui.theme.MindMapsTheme

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MindMapViewModel by viewModels()
        setContent {
            MindMapsTheme {
                with(viewModel) {
                    MindMap(
                        nodes = nodes,
                        offsets = offsets,
                        onDrag = ::onDrag,
                        onAdd = ::onAdd,
                        onDelete = ::onDelete,
                        onTextChange = ::onTextChange,
                        incidenceMatrix = incidenceMatrix
                    )
                }
            }
        }
    }
}
