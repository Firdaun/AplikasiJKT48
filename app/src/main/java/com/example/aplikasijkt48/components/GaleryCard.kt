package com.example.aplikasijkt48.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// --- MODEL DATA SEMENTARA (Sesuaikan dengan milikmu) ---
data class GalleryItem(
    val platform: String,
    val isVideo: Boolean,
    val image: String,
    val caption: String,
    val date: String,
    val member: String
)

@Composable
fun GalleryCard(
    item: GalleryItem,
    onClick: (GalleryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. Deteksi Ukuran Layar (Responsif)
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 768

    // Pengaturan ukuran dinamis berdasarkan device
    val cornerRadius = if (isTablet) 16.dp else 12.dp
    val cardHeight = if (isTablet) 320.dp else 240.dp
    val dateTextSize = if (isTablet) 10.sp else 9.sp
    val captionTextSize = if (isTablet) 12.sp else 11.sp
    val memberTextSize = if (isTablet) 12.sp else 9.sp
    val iconBoxSize = if (isTablet) 26.dp else 20.dp

    // 2. Menentukan Warna & Ikon Platform
    val platformColor = when (item.platform.lowercase()) {
        "instagram" -> Color(0xFFE1306C)
        "tiktok" -> Color(0xFFEE1D52)
        "x", "twitter" -> Color(0xFF1DA1F2)
        else -> Color(0xFFEE1D52)
    }

    val platformIcon = when (item.platform.lowercase()) {
        "instagram" -> getInstagramIcon()
        "tiktok" -> getTikTokIcon()
        "x", "twitter" -> getXIcon()
        else -> getTikTokIcon()
    }

    // 3. WADAH UTAMA (Menumpuk Layer 1, Layer 2, dan Kartu Utama)
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { onClick(item) }
    ) {
        // --- LAYER 1: Paling Belakang (Miring 5 Derajat) ---
        Box(
            modifier = Modifier
                .matchParentSize() // Ukurannya mengikuti kartu utama persis
                .padding(4.dp)     // Inset / agak masuk ke dalam
                .rotate(5f)        // Miring 5 derajat
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF1E1E38), Color(0xFF141428))
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(cornerRadius))
        )

        // --- LAYER 2: Tengah (Miring 2.5 Derajat) ---
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(2.dp)
                .rotate(2.5f)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF1A1A32), Color(0xFF10102A))
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(cornerRadius))
        )

        // --- LAYER 3: KARTU UTAMA (Posisi Lurus 0 Derajat di Paling Depan) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(cornerRadius))
                .background(Color(0xFF111120), RoundedCornerShape(cornerRadius))
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(cornerRadius))
                .clip(RoundedCornerShape(cornerRadius))
        ) {
            // Bagian Atas: Gambar / Thumbnail Video
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
            ) {
                // Gambar Utama (Coil AsyncImage)
                AsyncImage(
                    model = item.image,
                    contentDescription = item.caption,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Indikator Video Sederhana (Jika isVideo true)
                if (item.isVideo) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("VIDEO", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Gradient Gelap di Bagian Bawah Gambar (Agar teks terbaca)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x0D07070F), // Atas: Sangat transparan (0.05%)
                                    Color(0x6607070F), // Tengah: Setengah transparan (40%)
                                    Color(0xEB07070F)  // Bawah: Hampir solid gelap (92%)
                                )
                            )
                        )
                )

                // Teks Tanggal & Caption di Atas Gradient
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(if (isTablet) 16.dp else 8.dp)
                ) {
                    Text(
                        text = item.date.uppercase(),
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = dateTextSize,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.caption,
                        color = Color.White,
                        fontSize = captionTextSize,
                        fontWeight = FontWeight.Medium,
                        lineHeight = if (isTablet) 18.sp else 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Bagian Bawah: Footer (Nama Member & Ikon Platform)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .border(width = 1.dp, color = Color.White.copy(alpha = 0.05f))
                    .padding(horizontal = if (isTablet) 16.dp else 12.dp, vertical = if (isTablet) 12.dp else 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "@${item.member.lowercase()}",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = memberTextSize,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                // Lingkaran Ikon Platform
                Box(
                    modifier = Modifier
                        .size(iconBoxSize)
                        .background(platformColor.copy(alpha = 0.15f), CircleShape)
                        .border(1.dp, platformColor.copy(alpha = 0.35f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = platformIcon,
                        contentDescription = item.platform,
                        tint = platformColor,
                        modifier = Modifier.size(if (isTablet) 14.dp else 10.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// PEMBUATAN IKON VEKTOR MANUAL (Dari SVG Web)
// ==========================================

fun getInstagramIcon(): ImageVector {
    return ImageVector.Builder(name = "Instagram", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f).apply {
        path(stroke = androidx.compose.ui.graphics.SolidColor(Color.White), strokeLineWidth = 2.2f) {
            moveTo(7f, 2f)
            lineTo(17f, 2f)
            arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 7f)
            lineTo(22f, 17f)
            arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 17f, 22f)
            lineTo(7f, 22f)
            arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 17f)
            lineTo(2f, 7f)
            arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7f, 2f)
            close()
        }
        path(stroke = androidx.compose.ui.graphics.SolidColor(Color.White), strokeLineWidth = 2.2f) {
            moveTo(16f, 12f)
            arcTo(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8f, 12f)
            arcTo(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 16f, 12f)
            close()
        }
        path(fill = androidx.compose.ui.graphics.SolidColor(Color.White)) {
            moveTo(19f, 6.5f)
            arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 16f, 6.5f)
            arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 19f, 6.5f)
            close()
        }
    }.build()
}

fun getTikTokIcon(): ImageVector {
    return ImageVector.Builder(name = "TikTok", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f).apply {
        path(fill = androidx.compose.ui.graphics.SolidColor(Color.White)) {
            moveTo(19.59f, 6.69f)
            arcTo(4.83f, 4.83f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.82f, 2.44f)
            lineTo(15.82f, 2f)
            lineTo(12.37f, 2f)
            lineTo(12.37f, 15.67f)
            arcTo(2.89f, 2.89f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9.49f, 18.17f)
            arcTo(2.89f, 2.89f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.6f, 15.28f)
            arcTo(2.89f, 2.89f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9.49f, 12.39f)
            curveTo(9.77f, 12.39f, 10.03f, 12.43f, 10.28f, 12.49f)
            lineTo(10.28f, 9.01f)
            arcTo(6.27f, 6.27f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.49f, 8.96f)
            arcTo(6.34f, 6.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.15f, 15.2f)
            arcTo(6.34f, 6.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.49f, 21.54f)
            arcTo(6.34f, 6.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15.82f, 15.2f)
            lineTo(15.82f, 8.69f)
            arcTo(8.18f, 8.18f, 0f, isMoreThanHalf = false, isPositiveArc = false, 20.61f, 10.23f)
            lineTo(20.61f, 6.79f)
            arcTo(4.85f, 4.85f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.59f, 6.69f)
            close()
        }
    }.build()
}

fun getXIcon(): ImageVector {
    return ImageVector.Builder(name = "X", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f).apply {
        path(fill = androidx.compose.ui.graphics.SolidColor(Color.White)) {
            moveTo(18.244f, 2.25f)
            lineTo(21.552f, 2.25f)
            lineTo(14.325f, 10.51f)
            lineTo(22.827f, 21.75f)
            lineTo(16.17f, 21.75f)
            lineTo(11.456f, 15.519f)
            lineTo(6.055f, 21.75f)
            lineTo(2.739f, 21.75f)
            lineTo(10.469f, 12.915f)
            lineTo(1.254f, 2.25f)
            lineTo(8.08f, 2.25f)
            lineTo(12.339f, 7.881f)
            close()
            moveTo(17.083f, 19.77f)
            lineTo(18.916f, 19.77f)
            lineTo(7.084f, 4.126f)
            lineTo(5.117f, 4.126f)
            close()
        }
    }.build()
}