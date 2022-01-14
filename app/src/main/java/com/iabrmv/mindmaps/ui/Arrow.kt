package com.iabrmv.mindmaps.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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


@Composable
fun Arrow() {
    TODO()
}

// TODO("Make customisable Width")
class ArrowShape(private val widthStart: Float,
                 private val widthEnd: Float
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
            cubicPath(rectLeft, forward = true)
            lineTo(rectRight.right, rectRight.bottom)
            cubicPath(rectRight, forward = false)
            lineTo(rectLeft.left, rectLeft.top)
            close()
            fillType = PathFillType.EvenOdd
        }
        return Outline.Generic(path)
    }
}

fun Path.cubicPath(rect: Rect, forward: Boolean) {
    rect.run {
        val isConvex = size.height > size.width
        if (forward) {
            //reset()
            //moveTo(topLeft.x, topLeft.y)
            val (x1, y1) = centerLeft.coords
            val (x2, y2) =
                if (isConvex) bottomCenter.coords
                else centerRight.coords
            val (x3, y3) = bottomRight.coords
            cubicTo(
                x1 = x1, y1 = y1,
                x2 = x2, y2 = y2,
                x3 = x3, y3 = y3
            )
        } else {
            val (x1, y1) = centerRight.coords
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
                .size(200.dp, 100.dp)
                .background(
                    shape = ArrowShape(100f, 10f),
                    color = Color.Magenta
                )
        )
    }
}