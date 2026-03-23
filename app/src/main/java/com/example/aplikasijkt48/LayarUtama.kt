package com.example.aplikasijkt48

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(5.dp)
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