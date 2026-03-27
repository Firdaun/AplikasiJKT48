package com.example.aplikasijkt48.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import kotlinx.coroutines.delay

@Composable
fun Lightbox(
    item: GalleryItem,
    allItems: List<GalleryItem>,
    onClose: () -> Unit,
    onNavigate: (GalleryItem) -> Unit
) {
    val context = LocalContext.current
    var showCaption by remember { mutableStateOf(true) }

    val initialIndex = remember { allItems.indexOf(item).coerceAtLeast(0) }
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { allItems.size }
    )

    // Data dari foto yang SEDANG DILIHAT saat ini
    val currentItem = allItems[pagerState.currentPage]

    BackHandler { onClose() }

    // Sinkronisasi dengan parent (LayarUtama) & Reset Timer saat user menggeser (swipe)
    LaunchedEffect(pagerState.currentPage) {
        onNavigate(currentItem)
        showCaption = true
    }

    // Auto-Hide Caption dalam 2 detik
//    LaunchedEffect(pagerState.currentPage, showCaption) {
//        if (showCaption) {
//            delay(2000)
//            showCaption = false
//        }
//    }

    val platformColor = when (currentItem.platform.lowercase()) {
        "instagram" -> Color(0xFFE1306C)
        "tiktok" -> Color(0xFFEE1D52)
        "x", "twitter" -> Color(0xFF1DA1F2)
        else -> Color(0xFFEE1D52)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD904040A))
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showCaption = !showCaption
                },
        ) { page ->
            val pageItem = allItems[page]

            val imageModel = if (pageItem.isVideo) {
                ImageRequest.Builder(context)
                    .data(pageItem.imageUrl)
                    .decoderFactory(VideoFrameDecoder.Factory())
                    .videoFrameMillis(1000)
                    .build()
            } else {
                pageItem.imageUrl
            }
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(600.dp)
                    .offset(y = (-130).dp),
                contentAlignment = Alignment.Center,
            ) {
                coil.compose.AsyncImage(
                    model = imageModel,
                    contentDescription = "Preview",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                )

                if (pageItem.isVideo) {
                    Icon(
                        imageVector = Icons.Default.PlayCircleOutline,
                        contentDescription = "Play Video",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        // --- 3. OVERLAY CAPTION & TOMBOL (Mengikuti Foto Saat Ini) ---
        AnimatedVisibility(
            visible = showCaption,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 115.dp)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xA60A0A14), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = currentItem.platform,
                            color = platformColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    platformColor.copy(alpha = 0.15f),
                                    RoundedCornerShape(50)
                                )
                                .border(
                                    1.dp,
                                    platformColor.copy(alpha = 0.3f),
                                    RoundedCornerShape(50)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Text(
                            text = "@${currentItem.member.lowercase()}",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = currentItem.date,
                        color = Color.White.copy(alpha = 0.35f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val cleanFromEnter = currentItem.caption.replace(Regex("\\s+"), " ").trim()

                Text(
                    text = cleanFromEnter.takeIf { it.isNotBlank() } ?: "Tanpa caption",
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.1f))
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.1f))
                            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
                            .clickable {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Lihat post JKT48 ini: ${currentItem.postUrl}"
                                    )
                                }
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "Bagikan via"
                                    )
                                )
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            "Share",
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            "Share",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (currentItem.postUrl.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Color(0xFFEE1D52).copy(alpha = 0.1f))
                                .border(
                                    1.dp,
                                    Color(0xFFEE1D52).copy(alpha = 0.3f),
                                    RoundedCornerShape(50)
                                )
                                .clickable {
                                    val openIntent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.postUrl))
                                    context.startActivity(openIntent)
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                Icons.Default.OpenInNew,
                                "View",
                                tint = Color(0xFFEE1D52),
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                "View Post",
                                color = Color(0xFFEE1D52),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // --- 4. INDIKATOR DOTS & TOMBOL CLOSE (Di bawah layar) ---
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val totalDots = allItems.size
            val activeDot = pagerState.currentPage
            val maxVisibleDots = 7

            val start = if (totalDots <= maxVisibleDots) 0 else {
                maxOf(0, minOf(activeDot - 3, totalDots - maxVisibleDots))
            }
            val end = if (totalDots <= maxVisibleDots) totalDots else {
                minOf(totalDots, start + maxVisibleDots)
            }

            for (i in start until end) {
                // 👇 TAMBAHKAN key(i) DI SINI 👇
                androidx.compose.runtime.key(i) {

                    val isCurrent = i == activeDot

                    // 1. Animasi pelebaran Dot
                    val dotWidth by animateDpAsState(
                        targetValue = if (isCurrent) 24.dp else 6.dp,
                        label = "dotWidth"
                    )
                    // 2. Animasi perubahan warna Dot
                    val dotColor by animateColorAsState(
                        targetValue = if (isCurrent) Color(0xFFEE1D52) else Color.White.copy(alpha = 0.3f),
                        label = "dotColor"
                    )

                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(dotWidth)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }
        }
    }
}