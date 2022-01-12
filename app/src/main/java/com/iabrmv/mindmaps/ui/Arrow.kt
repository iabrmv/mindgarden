package com.iabrmv.mindmaps.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Arrow() {
    TODO()
}

// TODO("Make customisable Width")
class ArrowShape(private val width: Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val widthPx = width.value * density.density


        val path = Path().apply {
            reset()
            cubicTo(
                x1 = widthPx / 2, y1 = size.height / 2,
                x2 = size.width +  widthPx / 2, y2 = size.height / 2,
                x3 = size.width +  widthPx / 2, y3 = size.height
            )
        }
        return Outline.Generic(path)
    }
}
