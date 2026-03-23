package com.example.aplikasijkt48.components


import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    val paginationGroup = listOf("1", "2", "3", "...", totalPages.toString())

    Row(
        modifier = Modifier
            .fillMaxWidth()

            .drawBehind {
                drawLine(
                    color = Color.White.copy(alpha = 0.05f),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isFirstPage = currentPage == 1
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
                .clickable(enabled = !isFirstPage) { onPageChange(currentPage - 1) }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Prev",
                tint = if (isFirstPage) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Prev",
                color = if (isFirstPage) Color.White.copy(0.2f) else Color.White.copy(0.6f),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        paginationGroup.forEach { item ->
            val isSelected = item == currentPage.toString()
            val isEllipsis = item == "..."

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .drawBehind {

                        if (isSelected) {
                            drawIntoCanvas { canvas ->
                                val paint = Paint()
                                val frameworkPaint = paint.asFrameworkPaint()
                                frameworkPaint.color = Color(0xFFEE1D52).copy(alpha = 0.4f).toArgb()
                                frameworkPaint.maskFilter =
                                    BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)

                                canvas.drawRoundRect(
                                    -2f,
                                    -2f,
                                    size.width + 2f,
                                    size.height + 2f,
                                    50f,
                                    50f,
                                    paint
                                )
                            }
                        }
                    }
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFFEE1D52)
                        else Color.White.copy(alpha = 0.05f)
                    )
                    .border(
                        width = if (isSelected) 0.dp else 1.dp,
                        color = if (isSelected) Color.Transparent else Color.White.copy(0.1f),
                        shape = CircleShape
                    )
                    .clickable(enabled = !isEllipsis) {
                        if (!isEllipsis) onPageChange(item.toInt())
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        val isLastPage = currentPage == totalPages
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
                .clickable(enabled = !isLastPage) { onPageChange(currentPage + 1) }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Next",
                color = if (isLastPage) Color.White.copy(0.2f) else Color.White.copy(0.6f),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = if (isLastPage) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}