package com.iabrmv.mindmaps.ui.saves

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SaveItem(name: String, date: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 12.dp, horizontal = 8.dp)) {
        Text(text = name, fontSize = 24.sp)
        Text(text = date, fontSize = 10.sp)
    }
}
