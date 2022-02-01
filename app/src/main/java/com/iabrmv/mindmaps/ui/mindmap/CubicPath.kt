package com.iabrmv.mindmaps.ui.mindmap

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val infiniteTransition = rememberInfiniteTransition()
    val lightFactor by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Restart
        )
    )
    val brush = Brush.radialGradient(
        colors = listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
        center = Offset(start.x * lightFactor + (1f - lightFactor)* end.x,
            start.y * lightFactor + (1f - lightFactor)* end.y),
        radius = 100f
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
            brush = brush,
            path = path,
            style = Stroke(3.dp.toPx())
        )

    }
}

@Preview
@Composable
fun CubicPathPreview() {
//    Box(
//        Modifier
//            .fillMaxSize()
//            .background(Color.White)) {
//        Edge(Modifier.offset(100.dp, 100.dp).size(100.dp, 50.dp), color = Color.Magenta)
//    }
}