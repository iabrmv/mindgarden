package com.iabrmv.mindmaps.ui.saves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.iabrmv.mindmaps.ui.mindmap.Header

@Composable
fun SavesScreen(
    names: List<String>,
    dates: List<String>,
    onMindmapClick: (Int) -> Unit,
    onAddMindmap: (String, Offset) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    BoxWithConstraints {
        val centerOffset = with(LocalDensity.current) {
            Offset(maxWidth.toPx() / 2, maxHeight.toPx() / 2)
        }
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                Header("Mind Garden", modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                )
            }
            itemsIndexed(names) { index, name ->
                SaveItem(
                    name = name,
                    date = dates[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .clickable {
                            onMindmapClick(index)
                        }
                )
                Divider()
            }
        }
        FloatingActionButton(
            onClick = {
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }

        if (showDialog) {
            var name by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "Add new mindmap",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp)
                    )
                },
                text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        }
                    )
                },
                buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            onAddMindmap(name, centerOffset)
                        }) {
                            Text("OK")
                        }
                        TextButton(onClick = {
                            showDialog = false
                        }) {
                            Text("CANCEL")
                        }
                    }
                }
            )
        }
    }
}
