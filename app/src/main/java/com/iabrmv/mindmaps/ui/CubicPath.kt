package com.iabrmv.mindmaps.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
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

@Composable
fun Edge(modifier: Modifier = Modifier, start: Offset, end: Offset, color: Color) {
    val isConvex = abs(end.y - start.y) < abs(end.x - start.x)
    val x2 by animateFloatAsState(
        targetValue = if(isConvex) end.x else start.x,
        animationSpec = keyframes{ durationMillis = 300 }
    )
    val y2 by animateFloatAsState(
        targetValue = if(isConvex) start.y else end.y,
        animationSpec = keyframes{ durationMillis = 300 }
    )

    Canvas(modifier) {
        val path = Path().apply {
            moveTo(start.x, start.y)
            cubicTo(
                x1 = start.x, y1 = end.y,
                x2 = x2, y2 = y2,
                x3 = end.x, y3 = end.y
            )
        }
        drawPath(
            color = color,
            path = path,
            style = Stroke(1.dp.toPx())
        )

    }
}


@Composable
fun EdgeConic(modifier: Modifier = Modifier, start: Offset, end: Offset, color: Color) {

    val size = with(end - start) {
        with(LocalDensity.current) {
            DpSize(x.toDp(),  y.toDp())
        }
    }

    Box(modifier = modifier
        .offset{ start.round() }
        .size(size)
        .background(
            color = color,
            shape = ArrowShape(100f, 25f)
    ))
}
@Preview
@Composable
fun CubicPathPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        EdgeConic(Modifier, color = Color.Magenta, start = Offset(100f,100f), end = Offset(800f, 600f))
    }
}