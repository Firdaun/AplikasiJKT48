package com.example.aplikasijkt48.components

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.aplikasijkt48.R

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
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(600.dp)
                    .offset(y = (-130).dp),
                contentAlignment = Alignment.Center,
            ) {
                if (pageItem.isVideo) {
                    val isCurrentPage = page == pagerState.currentPage
                    ExoVideoPlayer(
                        videoUrl = pageItem.imageUrl,
                        isCurrentPage = isCurrentPage,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    val imageModel = pageItem.imageUrl
                    coil.compose.AsyncImage(
                        model = imageModel,
                        contentDescription = "Preview",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
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
                                        Intent(Intent.ACTION_VIEW, currentItem.postUrl.toUri())
                                    context.startActivity(openIntent)
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.OpenInNew,
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
@SuppressLint("InflateParams")
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoVideoPlayer(videoUrl: String, isCurrentPage: Boolean, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var isVideoEnded by remember {mutableStateOf(false)}
    var isPlaying by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()

            addListener(object: Player.Listener{
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        isVideoEnded = true
                    }
                }
                override fun onIsPlayingChanged(playing: Boolean) {
                    isPlaying = playing
                }
            })
        }
    }

    LaunchedEffect(isCurrentPage) {
        if (isCurrentPage) {
            if (isVideoEnded) {
                exoPlayer.seekTo(0)
                isVideoEnded = false
            }
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null

            ){
                if (isPlaying){
                    exoPlayer.pause()
                }else{
                    if (isVideoEnded){
                        exoPlayer.seekTo(0)
                        isVideoEnded = false
                    }
                    exoPlayer.play()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { ctx ->
                val inflater = LayoutInflater.from(ctx)
                (inflater.inflate(R.layout.player_view_custom, null) as PlayerView).apply {
                    useController = false
                    controllerAutoShow = false
                    isClickable = false
                    isFocusable = false
                }
            },
            update = { view ->
                view.player = exoPlayer
            },
            modifier = modifier
        )
        AnimatedVisibility(
            visible = !isPlaying || isVideoEnded,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier.size(100.dp), // Area sentuh/tampil diperbesar
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Replay Video",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }

}