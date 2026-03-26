package com.example.aplikasijkt48.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
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
    val currentIndex = allItems.indexOf(item)
    var showCaption by remember { mutableStateOf(true) }

    BackHandler { onClose() }

    LaunchedEffect(item, showCaption) {
        if (showCaption) {
            delay(2000)
            showCaption = false
        }
    }

    val platformColor = when (item.platform.lowercase()) {
        "instagram" -> Color(0xFFE1306C)
        "tiktok" -> Color(0xFFEE1D52)
        "x", "twitter" -> Color(0xFF1DA1F2)
        else -> Color(0xFFEE1D52)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD904040A))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showCaption = !showCaption
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
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

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.85f)
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                coil.compose.AsyncImage(
                    model = imageModel,
                    contentDescription = "Preview",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )

                if (item.isVideo) {
                    Icon(
                        imageVector = Icons.Default.PlayCircleOutline,
                        contentDescription = "Play Video",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(64.dp)
                    )
                }

                AnimatedVisibility(
                    visible = showCaption,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
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
                                    text = item.platform,
                                    color = platformColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .background(platformColor.copy(alpha = 0.15f), RoundedCornerShape(50))
                                        .border(1.dp, platformColor.copy(alpha = 0.3f), RoundedCornerShape(50))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                                Text(
                                    text = "@${item.member.lowercase()}",
                                    color = Color.White.copy(alpha = 0.4f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(
                                text = item.date,
                                color = Color.White.copy(alpha = 0.35f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = item.caption.takeIf { it.isNotBlank() } ?: "Tanpa caption",
                            color = Color.White,
                            fontSize = 12.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.White.copy(alpha = 0.1f)))
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
                                            putExtra(Intent.EXTRA_TEXT, "Lihat post JKT48 ini: ${item.postUrl}")
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Share, "Share", tint = Color.White.copy(alpha = 0.6f), modifier = Modifier.size(12.dp))
                                Text("Share", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }

                            if (item.postUrl.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(Color(0xFFEE1D52).copy(alpha = 0.1f))
                                        .border(1.dp, Color(0xFFEE1D52).copy(alpha = 0.3f), RoundedCornerShape(50))
                                        .clickable {
                                            val openIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.postUrl))
                                            context.startActivity(openIntent)
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.OpenInNew, "View", tint = Color(0xFFEE1D52), modifier = Modifier.size(12.dp))
                                    Text("View Post", color = Color(0xFFEE1D52), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentIndex > 0) {
                    IconButton(
                        onClick = { onNavigate(allItems[currentIndex - 1]) },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Prev", tint = Color.White)
                    }
                } else Spacer(modifier = Modifier.size(48.dp))

                if (currentIndex < allItems.lastIndex) {
                    IconButton(
                        onClick = { onNavigate(allItems[currentIndex + 1]) },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Next", tint = Color.White)
                    }
                } else Spacer(modifier = Modifier.size(48.dp))
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val start = maxOf(0, currentIndex - 3)
                val end = minOf(allItems.size, currentIndex + 4)

                for (i in start until end) {
                    val isCurrent = i == currentIndex
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (isCurrent) 24.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (isCurrent) Color(0xFFEE1D52) else Color.White.copy(alpha = 0.2f))
                    )
                }
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
                    .clickable { onClose() }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Close, "Close", tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                Text(
                    text = "Close",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
            }
        }
    }
}