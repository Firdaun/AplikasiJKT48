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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.aplikasijkt48.components.GalleryGrid
import com.example.aplikasijkt48.components.GalleryItem
import com.example.aplikasijkt48.components.InfoHasilPencarian
import com.example.aplikasijkt48.components.Pagination
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
        var halamanAktif by remember { mutableIntStateOf(1) }

        val daftarFoto = listOf(
            GalleryItem("Instagram", false, R.drawable.ic_launcher_background, "Theater hari ini pecah banget!", "22 MAR 2026", "Christy"),
            GalleryItem("TikTok", true, R.drawable.ic_launcher_background, "Lagi latihan dance nih", "21 MAR 2026", "Zee"),
            GalleryItem("X", false, R.drawable.ic_launcher_background, "Selamat pagi semuanya~", "20 MAR 2026", "Freya"),
            GalleryItem("Instagram", false, R.drawable.ic_launcher_background, "OOTD jalan-jalan", "19 MAR 2026", "Muthe"),
            GalleryItem("TikTok", true, R.drawable.ic_launcher_background, "Ikutan trend baru ah", "18 MAR 2026", "Gita")
        )

        // Tambahkan state ini di bawah var halamanAktif ...
        var postUrl by remember { mutableStateOf("") }
        val totalItem = daftarFoto.size // Anggap ini paging.total_item
        val globalAlbumCount = 5 // Angka bohongan untuk ngetes tombol kedua


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

                InfoHasilPencarian(
                    nickname = activeMemberName,
                    viewMode = viewMode,
                    totalItem = totalItem,
                    postUrl = postUrl,
                    globalAlbumCount = globalAlbumCount,
                    onShowAllClick = {
                        activeMemberName = ""
                        searchQuery = ""
                        postUrl = ""
                    },
                    onShowMemberPhotosClick = {
                        viewMode = "grid"
                        postUrl = ""
                    }
                )

                Spacer(modifier = Modifier.height(13.dp))

                GalleryGrid(
                    viewMode = viewMode,
                    items = daftarFoto,
                    onItemClick = { diklik ->
                        Log.d("TEST_KLIK", "Kartu ${diklik.member} diklik!")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Pagination(
                    currentPage = halamanAktif,
                    totalPages = 5,
                    onPageChange = { halamanBaru ->
                        halamanAktif = halamanBaru
                    }
                )

                Spacer(modifier = Modifier.height(50.dp))
            }

        }
    }
}

@Composable
fun DekorasiLatarBelakang() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .size(600.dp)
                .offset(x = (-150).dp, y = (-150).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFEE1D52).copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = 80.dp)
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

        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomStart)
                .offset(x = 120.dp, y = 50.dp)
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