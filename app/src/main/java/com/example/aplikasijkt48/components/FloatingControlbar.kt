package com.example.aplikasijkt48.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingControlBar(
    viewMode: String = "album",
    onViewModeChange: (String) -> Unit = {},
    activePlatform: String = "all",
    onPlatformChange: (String) -> Unit = {},
    searchQuery: String = "",
    onSearchChange: (String) -> Unit = {},
    onClear: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 768
    val containerPadding = if (isTablet) 24.dp else 12.dp
    val borderRadius = if (isTablet) 16.dp else 12.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(borderRadius))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(borderRadius))
            .padding(horizontal = containerPadding, vertical = 16.dp)
    ) {
        if (isTablet) {
            // === LAYOUT TABLET (1 Baris Lurus) ===
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToggleGroup(viewMode, onViewModeChange, isTablet, Modifier.width(280.dp))
                PlatformGroup(activePlatform, onPlatformChange, isTablet)
                SearchBar(searchQuery, onSearchChange, onClear, isTablet, Modifier.width(280.dp))
            }
        } else {
            // === LAYOUT MOBILE (2 Baris) ===
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Lebarnya dibagi 50% - 50% menggunakan weight(1f)
                    ToggleGroup(viewMode, onViewModeChange, isTablet, Modifier.weight(1f))
                    SearchBar(searchQuery, onSearchChange, onClear, isTablet, Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PlatformGroup(activePlatform, onPlatformChange, isTablet)
                }
            }
        }
    }
}

// ==========================================
// 1. KOMPONEN TOGGLE ALBUM & GRID
// ==========================================
@Composable
fun ToggleGroup(
    viewMode: String,
    onViewModeChange: (String) -> Unit,
    isTablet: Boolean,
    modifier: Modifier = Modifier
) {
    val fontSize = if (isTablet) 12.sp else 10.sp
    val iconSize = if (isTablet) 14.dp else 12.dp

    Row(
        modifier = modifier
            .height(40.dp)
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(50))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        // Tombol Album
        val isAlbum = viewMode == "album"
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .drawBehind { // Efek shadow glow merah
                    if (isAlbum) {
                        drawIntoCanvas { canvas ->
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = Color(0xFFEE1D52).copy(alpha = 0.4f).toArgb()
                            frameworkPaint.maskFilter = BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
                            canvas.drawRoundRect(0f, 10f, size.width, size.height + 10f, 50f, 50f, paint)
                        }
                    }
                }
                .clip(RoundedCornerShape(50))
                .background(
                    if (isAlbum) Brush.linearGradient(listOf(Color(0xFFEE1D52), Color(0xFFC01240)))
                    else SolidColor(Color.Transparent)
                )
                .clickable { onViewModeChange("album") },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.AutoMirrored.Filled.MenuBook, "Album", Modifier.size(iconSize), tint = if (isAlbum) Color.White else Color.White.copy(0.45f))
                Text("Album", color = if (isAlbum) Color.White else Color.White.copy(0.45f), fontSize = fontSize, fontWeight = FontWeight.SemiBold)
            }
        }

        // Tombol Grid
        val isGrid = viewMode == "grid"
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .drawBehind { // Efek shadow glow biru
                    if (isGrid) {
                        drawIntoCanvas { canvas ->
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = Color(0xFF00D4FF).copy(alpha = 0.35f).toArgb()
                            frameworkPaint.maskFilter = BlurMaskFilter(16f, BlurMaskFilter.Blur.NORMAL)
                            canvas.drawRoundRect(0f, 10f, size.width, size.height + 10f, 50f, 50f, paint)
                        }
                    }
                }
                .clip(RoundedCornerShape(50))
                .background(
                    if (isGrid) Brush.linearGradient(listOf(Color(0xFF00D4FF), Color(0xFF0099BB)))
                    else SolidColor(Color.Transparent)
                )
                .clickable { onViewModeChange("grid") },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(Icons.Default.GridView, "Grid", Modifier.size(iconSize), tint = if (isGrid) Color.White else Color.White.copy(0.45f))
                Text("Grid", color = if (isGrid) Color.White else Color.White.copy(0.45f), fontSize = fontSize, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ==========================================
// 2. KOMPONEN PLATFORM FILTER (ALL, IG, X)
// ==========================================
@Composable
fun PlatformGroup(activePlatform: String, onPlatformChange: (String) -> Unit, isTablet: Boolean) {
    val fontSize = if (isTablet) 12.sp else 10.5.sp
    val platforms = listOf(
        Triple("all", "All", Icons.Default.Language),
        Triple("instagram", "Instagram", Icons.Default.CameraAlt),
        Triple("twitter", "X/Twitter", Icons.Default.Close)
    )

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
        platforms.forEach { (key, label, icon) ->
            val isActive = activePlatform == key
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isActive) Color(0xFFEE1D52).copy(alpha = 0.1f) else Color.Transparent)
                    .clickable { onPlatformChange(key) },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(icon, label, Modifier.size(14.dp), tint = if (isActive) Color(0xFFEE1D52) else Color.White.copy(0.4f))
                    Text(label, color = if (isActive) Color.White else Color.White.copy(0.4f), fontSize = fontSize, fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium)
                }

                // Garis bawah menyala (indicator)
                if (isActive) {
                    Box(modifier = Modifier.matchParentSize().padding(horizontal = 12.dp)) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(2.dp)
                                .drawBehind {
                                    drawIntoCanvas { canvas ->
                                        val paint = Paint()
                                        val frameworkPaint = paint.asFrameworkPaint()
                                        frameworkPaint.color = Color(0xFFEE1D52).toArgb()
                                        frameworkPaint.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
                                        canvas.drawRoundRect(0f, 0f, size.width, size.height, 50f, 50f, paint)
                                    }
                                }
                                .background(Brush.horizontalGradient(listOf(Color(0xFFEE1D52), Color(0xFFFF6B9D))), RoundedCornerShape(50))
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. KOMPONEN SEARCH BAR
// ==========================================
@Composable
fun SearchBar(searchQuery: String, onSearchChange: (String) -> Unit, onClear: () -> Unit, isTablet: Boolean, modifier: Modifier = Modifier) {
    var isFocused by remember { mutableStateOf(false) }
    val fontSize = if (isTablet) 12.sp else 10.sp

    Row(
        modifier = modifier
            .height(40.dp)
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(50))
            .border(
                width = if (isFocused) 1.5.dp else 1.dp,
                color = if (isFocused) Color(0xFFEE1D52).copy(alpha = 0.5f) else Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(50)
            )
            .drawBehind { // Efek shadow saat Search diklik (focus)
                if (isFocused) {
                    drawIntoCanvas { canvas ->
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.color = Color(0xFFEE1D52).copy(alpha = 0.12f).toArgb()
                        frameworkPaint.strokeWidth = 10f
                        frameworkPaint.style = android.graphics.Paint.Style.STROKE
                        canvas.drawRoundRect(0f, 0f, size.width, size.height, 50.dp.toPx(), 50.dp.toPx(), paint)
                    }
                }
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, "Search", tint = Color.White.copy(0.35f), modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            textStyle = TextStyle(color = Color.White, fontSize = fontSize, platformStyle = PlatformTextStyle(includeFontPadding = false)),
            cursorBrush = SolidColor(Color(0xFFEE1D52)),
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isFocused = it.isFocused }, // Deteksi kursor masuk
            decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                    Text("Search members…", color = Color.White.copy(0.4f), fontSize = fontSize)
                }
                innerTextField()
            }
        )
        if (searchQuery.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(Color.White.copy(0.1f), CircleShape)
                    .clickable { onClear() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Close, "Clear", tint = Color.White.copy(0.7f), modifier = Modifier.size(10.dp))
            }
        }
    }
}