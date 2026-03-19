package com.example.aplikasijkt48.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasijkt48.R

data class MemberProfile(val name: String, val team: String, val imageResId: Int)
data class TeamStyle(val color: Color, val gradientEnd: Color)

val teamStyles = mapOf(
    "love" to TeamStyle(Color(0xFFEE1D52), Color(0xFFFF6B9D)),
    "passion" to TeamStyle(Color(0xFFFFD700), Color(0xFFFFEB73)),
    "dream" to TeamStyle(Color(0xFF00D4FF), Color(0xFF7DF9FF)),
    "trainee" to TeamStyle(Color(0xFF94A3B8), Color(0xFFCBD5E1))
)

val dummyMembers = listOf(
    MemberProfile("freya", "dream", R.drawable.ic_launcher_background),
    MemberProfile("christy", "passion", R.drawable.ic_launcher_background),
    MemberProfile("fritzy", "love", R.drawable.ic_launcher_background),
    MemberProfile("ekin", "trainee", R.drawable.ic_launcher_background),
    MemberProfile("muthe", "passion", R.drawable.ic_launcher_background),
    MemberProfile("marsha", "dream", R.drawable.ic_launcher_background)
)

@Composable
fun StoryCarousel(
    activeMember: String,
    onSelectMember: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Updated Nov 10, 2026",
                    color = Color(0xFF00D4FF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(Color(0xFF00D4FF).copy(alpha = 0.08f), RoundedCornerShape(50))
                        .border(1.dp, Color(0xFF00D4FF).copy(alpha = 0.2f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Media Gallery",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 40.sp,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
                Row {
                    Text(
                        text = "JKT48",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 40.sp,
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFEE1D52),
                                    Color(0xFFFF6B9D),
                                    Color(0xFFEE1D52)
                                )
                            ),
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Official",
                        color = Color.White.copy(alpha = 0.35f),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 40.sp,
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    )
                }

            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "10,245",
                        color = Color(0xFF00D4FF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "TOTAL MEDIA",
                        color = Color.White.copy(alpha = 0.35f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "40",
                        color = Color(0xFFA855F7),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "MEMBERS",
                        color = Color.White.copy(alpha = 0.35f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )

                }
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(dummyMembers) { member ->
                val isActive = activeMember == member.name
                val style = teamStyles[member.team] ?: teamStyles["love"]!!

                val scale by animateFloatAsState(
                    targetValue = if (isActive) 1.1f else 1.0f,
                    label = "scale"
                )
                val interactionSource = remember { MutableInteractionSource() }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onSelectMember(member.name) }
                        )
                        .scale(scale)
                ) {
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .background(
                                brush = if (isActive) Brush.linearGradient(
                                    listOf(
                                        style.color,
                                        style.gradientEnd
                                    )
                                ) else SolidColor(Color.White.copy(alpha = 0.12f)),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .background(
                                if (isActive) Color(0xFF07070F) else Color.Transparent,
                                CircleShape
                            )
                            .padding(2.dp)
                    ) {
                        Image(
                            painter = painterResource(id = member.imageResId),
                            contentDescription = member.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            colorFilter = if (!isActive) ColorFilter.colorMatrix(ColorMatrix().apply {
                                setToSaturation(
                                    0f
                                )
                            }) else null
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = member.name,
                        color = if (isActive) Color.White else Color.White.copy(alpha = 0.4f),
                        fontSize = 11.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = member.team,
                        color = style.color,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        modifier = Modifier
                            .background(style.color.copy(alpha = 0.13f), RoundedCornerShape(50))
                            .border(1.dp, style.color.copy(alpha = 0.26f), RoundedCornerShape(50))
                            .padding(horizontal = 6.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}