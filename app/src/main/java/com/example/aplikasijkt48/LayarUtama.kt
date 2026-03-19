package com.example.aplikasijkt48

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasijkt48.navbar.TopNavbar
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    val context = LocalContext.current
    var fotoSaatIni by remember { mutableIntStateOf(R.drawable.fritzy_dimalamhari) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopNavbar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(vertical = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
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
                painter = painterResource(id = fotoSaatIni),
                contentDescription = "Foto Bersama",
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .clip(RoundedCornerShape(20.dp))

            )
            Button(
                onClick = {
                    fotoSaatIni = if (fotoSaatIni == R.drawable.fritzy_dimalamhari) {
                        R.drawable.ic_launcher_background
                    } else {
                        R.drawable.fritzy_dimalamhari
                    }
                    Toast.makeText(
                        context,
                        "Tombol telah di ubah",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2883E5),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = "Interact",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6_PRO
)
@Composable
fun DesainLayarUtamaPreview() {
    AplikasiJKT48Theme {
        DesainLayarUtama()
    }
}