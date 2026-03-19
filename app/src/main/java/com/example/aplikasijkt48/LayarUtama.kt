package com.example.aplikasijkt48

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikasijkt48.components.StoryCarousel
import com.example.aplikasijkt48.navbar.TopNavbar
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme
import android.util.Log
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopNavbar()
        }
    ) { innerPadding ->
        var activeMemberName by remember { mutableStateOf("freya") }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 13.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StoryCarousel(
                activeMember = activeMemberName,
                onSelectMember = { namaMember ->
                    activeMemberName = namaMember
                }
            )
            var uangBy by remember { mutableStateOf(1000) }

            // 2. Uji coba pakai '=' (Pegang Brankas langsung)
            val ingat = remember { mutableStateOf(1000) }

            Button(
                onClick = {
                    // Kita ubah nilainya
                    uangBy = uangBy + 500
                    ingat.value = ingat.value + 500

                    // KITA PRINT KE TERMINAL (LOGCAT)
                    // Formatnya: Log.d("NAMA_TAG", "Pesan yang mau di-print")
                    Log.d("TEST_FAHRUL", "Nilai uangBy sekarang: $uangBy")
                    Log.d("TEST_FAHRUL", "Nilai uangSamaDengan sekarang: ${ingat.value}")
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Cek Console Log (Logcat)!")
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