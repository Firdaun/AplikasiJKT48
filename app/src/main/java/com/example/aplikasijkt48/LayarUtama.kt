package com.example.aplikasijkt48

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Photo Profile",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                            Text(
                                text = "Fahrul Keren",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                            Text(
                                text = "Jacqueline",
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                ProfilMember(
                    nama = "Freya",
                    hobi = "menari",
                    generasi = "7"
                )

                ProfilMember(
                    nama = "Ekin",
                    hobi = "nyanyi",
                    generasi = "13"
                )
            }
            Image(
                painter = painterResource(id = R.drawable.fritzy_dimalamhari), // Sesuaikan id gambar
                contentDescription = "Foto Bersama",
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp)
                    .clip(RoundedCornerShape(20.dp))

            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DesainLayarUtamaPreview() {
    AplikasiJKT48Theme {
        DesainLayarUtama()
    }
}