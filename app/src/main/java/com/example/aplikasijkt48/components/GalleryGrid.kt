package com.example.aplikasijkt48.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis

@Composable
fun GalleryGrid(
    viewMode: String,
    items: List<GalleryItem>,
    onItemClick: (GalleryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 768

    // --- KONDISI 1: JIKA DATA KOSONG ---
    if (items.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BrokenImage,
                contentDescription = "Empty",
                tint = Color.White.copy(alpha = 0.2f),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Tidak ada media yang ditemukan.",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = if (isTablet) 14.sp else 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
        return
    }

    // --- KONDISI 2: MODE GRID (Kotak-kotak kecil ala IG) ---
    if (viewMode == "photo") {
        val columns = if (isTablet) 5 else 3 // Tablet 5 kolom, HP 3 kolom

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items.chunked(columns).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rowItems.forEach { item ->
                        GridSquareItem(
                            item = item,
                            onClick = onItemClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Isi ruang kosong kalau jumlah foto di baris terakhir kurang dari jumlah kolom
                    repeat(columns - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        return
    }

    // --- KONDISI 3: MODE ALBUM (Default) ---
    val albumColumns = if (isTablet) 4 else 2 // Tablet 4 kolom, HP 2 kolom

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.chunked(albumColumns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                rowItems.forEach { item ->
                    GalleryCard(
                        item = item,
                        onClick = onItemClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(albumColumns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

// Komponen Card khusus untuk Mode Grid (Kotak 1:1)
@Composable
fun GridSquareItem(
    item: GalleryItem,
    onClick: (GalleryItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .aspectRatio(1f) // Bikin jadi persegi sama sisi
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(12.dp))
            .clickable { onClick(item) }
    ) {

        val imageModel = if (item.isVideo) {
            ImageRequest.Builder(context)
                .data(item.imageUrl)
                .decoderFactory(VideoFrameDecoder.Factory())
                .videoFrameMillis(1000)
                .build()
        } else {
            item.imageUrl
        }

        coil.compose.AsyncImage(
            model = imageModel,
            contentDescription = item.caption,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Badge Video di Pojok Kiri Atas
        if (item.isVideo) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xFFEE1D52).copy(alpha = 0.85f), RoundedCornerShape(50))
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "▶ VIDEO",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
            }
        }
    }
}