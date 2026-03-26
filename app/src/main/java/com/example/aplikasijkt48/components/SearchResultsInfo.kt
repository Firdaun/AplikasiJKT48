package com.example.aplikasijkt48.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchResultsInfo(
    nickname: String,
    viewMode: String,
    totalItem: Int,
    postUrl: String,
    globalAlbumCount: Int,
    onShowAllClick: () -> Unit,
    onShowMemberPhotosClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp > 768

    // Ukuran teks responsif
    val titleSize = if (isTablet) 13.sp else 11.5.sp
    val badgeSize = if (isTablet) 11.sp else 10.sp
    val buttonSize = if (isTablet) 13.sp else 10.sp

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp) // gap-1
        ) {
            val prefix = if (nickname.isNotEmpty()) "$nickname's" else "All"
            val suffix = if (viewMode == "photo") "photos" else "albums"

            Text(
                text = "$prefix $suffix".uppercase(),
                color = Color.White.copy(alpha = 0.35f),
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )

            Text(
                text = "$totalItem results",
                color = Color(0xFFEE1D52),
                fontSize = badgeSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFFEE1D52).copy(alpha = 0.1f), RoundedCornerShape(50))
                    .border(1.dp, Color(0xFFEE1D52).copy(alpha = 0.25f), RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // gap-2
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol 2: Show Member Photos (Hanya muncul jika kondisi terpenuhi)
            if (nickname.isNotEmpty() && postUrl.isNotEmpty() && globalAlbumCount > 1) {
                Text(
                    text = "show $nickname's photos".uppercase(),
                    color = Color.White.copy(alpha = 0.35f),
                    fontSize = buttonSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onShowMemberPhotosClick() },
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
            }

            // Tombol 1: Show All (Hanya muncul jika ada nickname yang dipilih)
            if (nickname.isNotEmpty()) {
                val buttonSuffix = if (viewMode == "photo") "photos" else "albums"
                Text(
                    text = "SHOW ALL $buttonSuffix".uppercase(),
                    color = Color.White.copy(alpha = 0.35f),
                    fontSize = buttonSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onShowAllClick() },
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
            }
        }
    }
}