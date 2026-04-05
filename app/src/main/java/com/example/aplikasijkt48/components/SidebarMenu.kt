package com.example.aplikasijkt48.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasijkt48.R

@Composable
fun SidebarMenu(onClose: () -> Unit) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF0D0D1A),
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Photo profile",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text(
                        text = "Fahrul Keren",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Jacqueline",
                        color = Color(0xFFEE1D52),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "\uD83C\uDFE0 Home", color = Color.White, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "⭐ Bookmarks", color = Color.White, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "⚙\uFE0F Bookmarks",
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClose() }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Dreview() {
    SidebarMenu(onClose = {})
}