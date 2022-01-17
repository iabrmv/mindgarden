package com.iabrmv.mindmaps.ui.saves

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SaveItem(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, fontSize = 24.sp)
}
