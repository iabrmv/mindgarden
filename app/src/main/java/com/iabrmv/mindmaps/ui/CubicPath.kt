package com.iabrmv.mindmaps.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.abs

fun cubicPath(start: Offset, end: Offset) = Path().apply {
    moveTo(start.x, start.y)

    if(abs(end.y - start.y) < abs(end.x - start.x)) {
        cubicTo(
            x1 = start.x, y1 = end.y,
            x2 = end.x, y2 = start.y,
            x3 = end.x, y3 = end.y
        )
    } else {
        cubicTo(
            x1 = start.x, y1 = end.y,
            x2 = start.x, y2 = end.y,
            x3 = end.x, y3 = end.y
        )
    }
}