package com.example.aplikasijkt48.navbar

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

@Composable
fun LogoJKT48(modifier: Modifier = Modifier) {
    val warnaGradasi = Brush.linearGradient(
        colors = listOf(
            Color(0xFFEE1D52),
            Color(0xFFC01240)
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(40.dp)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()

                    frameworkPaint.color = Color(0xFFEE1D52).copy(alpha = 0.70f).toArgb()
                    frameworkPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
                    canvas.drawRoundRect(
                        left = 0f,
                        top = 4f,
                        right = size.width,
                        bottom = size.height + 4f,
                        radiusX = 10.dp.toPx(),
                        radiusY = 10.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .background(brush = warnaGradasi, shape = RoundedCornerShape(10.dp))
    ) {
        Canvas(
            modifier = Modifier
                .size(24.dp)
                .scale(0.9f)
        ) {
            val skalaLayar = this.density
            scale(scaleX = skalaLayar, scaleY = skalaLayar, pivot = Offset.Zero) {
                val gayaGaris = Stroke(
                    width = 2f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )

                val pathBintang = PathParser().parsePathString(
                    "M11.017 2.814a1 1 0 0 1 1.966 0l1.051 5.558a2 2 0 0 0 1.594 1.594l5.558 1.051a1 1 0 0 1 0 1.966l-5.558 1.051a2 2 0 0 0-1.594 1.594l-1.051 5.558a1 1 0 0 1-1.966 0l-1.051-5.558a2 2 0 0 0-1.594-1.594l-5.558-1.051a1 1 0 0 1 0-1.966l5.558-1.051a2 2 0 0 0 1.594-1.594z"
                ).toPath()
                drawPath(path = pathBintang, color = Color.White, style = gayaGaris)

                val pathVertical = PathParser().parsePathString("M20 2v4").toPath()
                drawPath(path = pathVertical, color = Color.White, style = gayaGaris)

                val pathHorizontal = PathParser().parsePathString("M22 4h-4").toPath()
                drawPath(path = pathHorizontal, color = Color.White, style = gayaGaris)

                drawCircle(
                    color = Color.White,
                    radius = 2f,
                    center = Offset(4f, 20f),
                    style = gayaGaris
                )
            }
        }
    }
}