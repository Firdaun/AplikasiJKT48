package com.example.aplikasijkt48.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopNavbar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF07070F))
            .statusBarsPadding()
            .height(70.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LogoJKT48()
            Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "JKT48",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    val warnaMedia = Color(0xFFF0244C)
                    Text(
                        text = "MEDIA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = warnaMedia,
                        modifier = Modifier
                            .background(
                                color = warnaMedia.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = warnaMedia,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),

                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
                Text(
                    text = "Official Fan Gallery",
                    color = Color.White,
                    fontSize = 13.sp,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
        Hamburger()
    }
}