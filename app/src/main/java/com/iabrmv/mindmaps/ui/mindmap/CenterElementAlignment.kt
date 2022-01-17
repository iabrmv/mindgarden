package com.iabrmv.mindmaps.ui

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

val CenterElementAlignment
get() =  Alignment { size, space, layoutDirection ->
    IntOffset(
        -size.width / 2,
        -size.height / 2
    )
}