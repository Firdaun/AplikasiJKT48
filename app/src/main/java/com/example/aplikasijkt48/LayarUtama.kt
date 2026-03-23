package com.example.aplikasijkt48

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikasijkt48.components.FloatingControlBar
import com.example.aplikasijkt48.components.StoryCarousel
import com.example.aplikasijkt48.navbar.TopNavbar
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF07070F),
        topBar = {
            TopNavbar()
        }
    ) { innerPadding ->
        var activeMemberName by remember { mutableStateOf("christy") }
        var viewMode by remember { mutableStateOf("album") }
        var activePlatform by remember { mutableStateOf("all") }
        var searchQuery by remember { mutableStateOf("") }

        val daftarFoto = listOf(
            com.example.aplikasijkt48.components.GalleryItem("Instagram", false, R.drawable.kabesha_angelina_christy, "Theater hari ini pecah banget!", "22 MAR 2026", "Christy"),
            com.example.aplikasijkt48.components.GalleryItem("TikTok", true, R.drawable.kabesha_angelina_christy, "Lagi latihan dance nih", "21 MAR 2026", "Zee"),
            com.example.aplikasijkt48.components.GalleryItem("X", false, R.drawable.kabesha_angelina_christy, "Selamat pagi semuanya~", "20 MAR 2026", "Freya"),
            com.example.aplikasijkt48.components.GalleryItem("Instagram", false, R.drawable.kabesha_angelina_christy, "OOTD jalan-jalan", "19 MAR 2026", "Muthe"),
            com.example.aplikasijkt48.components.GalleryItem("TikTok", true, R.drawable.kabesha_angelina_christy, "Ikutan trend baru ah", "18 MAR 2026", "Gita")
        )
        Box(modifier = Modifier.fillMaxSize()) {
            DekorasiLatarBelakang()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 13.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StoryCarousel(
                    modifier = Modifier.padding(top = 20.dp),
                    activeMember = activeMemberName,
                    onSelectMember = { namaMember ->
                        activeMemberName = namaMember
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                FloatingControlBar(
                    viewMode = viewMode,
                    onViewModeChange = { viewMode = it },
                    activePlatform = activePlatform,
                    onPlatformChange = { activePlatform = it },
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    onClear = { searchQuery = "" }
                )

                Spacer(modifier = Modifier.height(13.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(5.dp)
                ) {
                    daftarFoto.chunked(2).forEach { barisFoto ->
                        androidx.compose.foundation.layout.Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(
                                5.dp
                            )
                        ) {
                            barisFoto.forEach { foto ->
                                com.example.aplikasijkt48.components.GalleryCard(
                                    item = foto,
                                    onClick = { diklik ->
                                        Log.d("TEST_KLIK", "Kartu ${diklik.member} diklik!")
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (barisFoto.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }

        }
    }
}

@Composable
fun DekorasiLatarBelakang() {
    // Wadah seukuran layar penuh (fixed inset-0)
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Cahaya Merah Pink (Pojok Kiri Atas)
        Box(
            modifier = Modifier
                .size(600.dp) // Ukuran besar (w-150)
                .offset(x = (-150).dp, y = (-150).dp) // absolute -top-50 -left-50
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFEE1D52).copy(alpha = 0.08f), // Titik tengah
                            Color.Transparent // Pudar di ujung (blur)
                        )
                    ),
                    shape = CircleShape
                )
        )

        // 2. Cahaya Cyan (Kanan Atas)
        Box(
            modifier = Modifier
                .size(350.dp) // w-125
                .align(Alignment.TopEnd) // Nempel di kanan atas
                .offset(x = 100.dp, y = 80.dp) // top-25 -right-50
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00D4FF).copy(alpha = 0.03f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // 3. Cahaya Ungu (Bawah Tengah)
        Box(
            modifier = Modifier
                .size(300.dp) // w-100
                .align(Alignment.BottomStart)
                .offset(x = 120.dp, y = 50.dp) // bottom-50 left-[40%]
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFA855F7).copy(alpha = 0.04f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
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