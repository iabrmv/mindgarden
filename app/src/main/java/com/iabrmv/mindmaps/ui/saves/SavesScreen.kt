package com.iabrmv.mindmaps.ui.saves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SavesScreen(
    names: List<String>,
    onMindmapClick: (Int) -> Unit,
    onAddMindmap: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(names) { index, name ->
            SaveItem(
                text = name,
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

    FloatingActionButton(onClick = {

        showDialog = true
    }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
    }

    if(showDialog) {
        var name by remember { mutableStateOf("" ) }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text("Add new mindmap")
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
                TextButton(onClick = {
                    onAddMindmap(name)
                }) {
                    Text("OK")
                }
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("CANCEL")
                }
            }
        )
    }
}
