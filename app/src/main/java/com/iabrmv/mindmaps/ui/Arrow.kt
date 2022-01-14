package com.iabrmv.mindmaps.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.abs


class EdgeCubicShape(private val convexFactor: Float): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val rect = Rect(offset = Offset.Zero, size = size)
        val path = Path().apply {
            cubicPath(
                rect = rect,
                forward = true,
                convexFactor = convexFactor
            )
        }
        return Outline.Generic(path)
    }
}

// TODO("Make customisable Width")
class ArrowShape(private val widthStart: Float,
                 private val widthEnd: Float,
                 private val convexFactor: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val rectLeft = Rect(
            Offset(0f, 0f),
            Offset(size.width - widthEnd, size.height)
        )
        val rectRight = Rect(
            Offset(widthStart, 0f),
            Offset(size.width, size.height)
        )

        val path = Path().apply {
            reset()
            cubicPath(rectLeft, forward = true, convexFactor = convexFactor)
            lineTo(rectRight.right, rectRight.bottom)
            cubicPath(rectRight, forward = false, convexFactor = convexFactor)
            lineTo(rectLeft.left, rectLeft.top)
            close()
            fillType = PathFillType.EvenOdd
        }
        return Outline.Generic(path)
    }
}

fun Path.cubicPath(rect: Rect, forward: Boolean, convexFactor: Float) {
    rect.run {
        if (forward) {
            val (x1, y1) = centerLeft.coords
            val (x2, y2) = (bottomCenter * convexFactor + centerRight * (1f - convexFactor)).coords
            val (x3, y3) = bottomRight.coords
            cubicTo(
                x1 = x1, y1 = y1,
                x2 = x2, y2 = y2,
                x3 = x3, y3 = y3
            )
        } else {
            val (x1, y1) = (bottomCenter * convexFactor + centerRight * (1f - convexFactor)).coords
            val (x2, y2) = centerLeft.coords
            val (x3, y3) = topLeft.coords
            cubicTo(
                x1 = x1, y1 = y1,
                x2 = x2, y2 = y2,
                x3 = x3, y3 = y3
            )
        }
    }
}

val Offset.coords: Pair<Float, Float> get() = Pair(x, y)

@Preview
@Composable
fun ArrowShapePreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)){
        Box(
            Modifier
                .offset(100.dp, 100.dp)
                .size(220.dp, 200.dp)
                .background(
                    shape = ArrowShape(100f, 10f, convexFactor = 0f),
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Magenta,
                            MaterialTheme.colors.primary
                        )
                    )
                )
        )
    }
}