package com.example.aplikasijkt48

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun ProfilMember(nama: String, hobi: String, generasi: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nama aku : $nama",
                fontSize = 14.sp
            )
            Text(
                text = "Hobi aku : $hobi",
                fontSize = 14.sp
            )
            Text(
                text = "Generasi : $generasi",
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Photo Profile",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Fahrul Keren",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(text = "Jacqueline")

        }

    }
}
