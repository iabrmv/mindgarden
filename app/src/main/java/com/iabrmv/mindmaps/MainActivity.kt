package com.iabrmv.mindmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import com.iabrmv.mindmaps.ui.MindMap
import com.iabrmv.mindmaps.ui.MindMapPreview
import com.iabrmv.mindmaps.ui.NodeAlternativePreview
import com.iabrmv.mindmaps.ui.TestPreview
import com.iabrmv.mindmaps.ui.theme.MindMapsTheme

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MindMapViewModel by viewModels()
        setContent {
            // TestPreview()
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
