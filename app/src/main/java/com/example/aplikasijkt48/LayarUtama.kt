package com.example.aplikasijkt48

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasijkt48.components.FloatingControlBar
import com.example.aplikasijkt48.components.GalleryGrid
import com.example.aplikasijkt48.components.GalleryItem
import com.example.aplikasijkt48.components.InfoHasilPencarian
import com.example.aplikasijkt48.components.Pagination
import com.example.aplikasijkt48.components.StoryCarousel
import com.example.aplikasijkt48.navbar.TopNavbar
import com.example.aplikasijkt48.network.GalleryViewModel
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama(
    viewModel: GalleryViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF07070F),
        topBar = {
            TopNavbar()
        }
    ) { innerPadding ->
        var activeMemberName by remember { mutableStateOf("") }
        var viewMode by remember { mutableStateOf("album") }
        var activePlatform by remember { mutableStateOf("all") }
        var searchQuery by remember { mutableStateOf("") }
        var halamanAktif by remember { mutableIntStateOf(1) }
        var postUrl by remember { mutableStateOf("") }
        var isRefreshing by remember { mutableStateOf(false) }
        val pullRefreshState = rememberPullToRefreshState()

        val daftarFoto = viewModel.fotoList.map { apiData ->
            GalleryItem(
                platform = apiData.source,
                isVideo = apiData.mediaType == "VIDEO" || apiData.srcUrl.endsWith(".mp4"),
                // Menempelkan IP lokal ke path foto (/photos/...)
                imageUrl = "http://192.168.1.7:3000${apiData.srcUrl}",
                caption = apiData.caption ?: "Tanpa Caption",
                date = apiData.postedAt.take(10), // Ambil tanggalnya saja (YYYY-MM-DD)
                member = apiData.member?.nickname ?: "JKT48"
            )
        }

        val totalItem = viewModel.pagingInfo?.totalItem ?: 0
        val totalPages = viewModel.pagingInfo?.totalPage ?: 1
        val globalAlbumCount = 5 // Dummy logic sementara untuk tombol "Show Photos"

        LaunchedEffect(activeMemberName, viewMode, activePlatform, searchQuery, halamanAktif, postUrl) {
            if (searchQuery.isNotEmpty()) {
                delay(300)
            }

            viewModel.fetchPhotos(
                page = halamanAktif,
                size = if (viewMode == "album") 8 else 28,
                source = activePlatform,
                nickname = activeMemberName,
                mode = viewMode,
                search = null,
                postUrl = postUrl
            )
        }

        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = pullRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true

                viewModel.fetchPhotos(
                    page = halamanAktif,
                    size = if (viewMode == "album") 8 else 28,
                    source = activePlatform,
                    nickname = activeMemberName,
                    mode = viewMode,
                    search = searchQuery.ifEmpty { null },
                    postUrl = postUrl,
                    forceRefresh = true
                )

                isRefreshing = false
            }
        ) {
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
                        searchQuery = ""
                        halamanAktif = 1
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                FloatingControlBar(
                    viewMode = viewMode,
                    onViewModeChange = {
                        viewModel.fetchPhotos(
                            page = 1,
                            size = if (it == "album") 8 else 28,
                            source = activePlatform,
                            nickname = activeMemberName,
                            mode = it,
                            search = searchQuery.ifEmpty { null },
                            postUrl = postUrl,
                        )
                        viewMode = it
                        halamanAktif = 1
                    },
                    activePlatform = activePlatform,
                    onPlatformChange = {
                        activePlatform = it
                        halamanAktif = 1
                    },
                    searchQuery = searchQuery,
                    onSearchChange = {
                        searchQuery = it
                        activeMemberName = it.lowercase().trim()
                        halamanAktif = 1
                    },
                    onClear = {
                        searchQuery = ""
                        activeMemberName = ""
                        halamanAktif = 1
                    }
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
                        halamanAktif = 1
                    },
                    onShowMemberPhotosClick = {
                        viewModel.fetchPhotos(
                            page = 1,
                            size = 28,
                            source = activePlatform,
                            nickname = activeMemberName,
                            mode = "photo",
                            search = searchQuery.ifEmpty { null },
                            postUrl = ""
                        )
                        viewMode = "photo"
                        postUrl = ""
                        halamanAktif = 1
                    }
                )

                Spacer(modifier = Modifier.height(13.dp))

                if (viewModel.isLoading) {
                    Box(modifier = Modifier.height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFEE1D52))
                    }
                } else if (viewModel.errorMessage != null) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .height(200.dp), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${viewModel.errorMessage}", color = Color.Red)
                    }
                } else {
                    GalleryGrid(
                        viewMode = viewMode,
                        items = daftarFoto,
                        onItemClick = { diklik ->
                            if (viewMode == "album") {
                                val indexData = daftarFoto.indexOf(diklik)
                                if (indexData != -1) {
                                    val targetPostUrl = viewModel.fotoList[indexData].postUrl ?: ""
                                    val targetNickname = diklik.member.lowercase()
                                    viewModel.fetchPhotos(
                                        page = 1,
                                        size = 28,
                                        source = activePlatform,
                                        nickname = targetNickname,
                                        mode = "photo",
                                        search = searchQuery.ifEmpty { null },
                                        postUrl = targetPostUrl
                                    )

                                    postUrl = targetPostUrl
                                    viewMode = "photo"
                                    halamanAktif = 1
                                    activeMemberName = diklik.member.lowercase()
                                }
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (totalPages > 1) {
                    Pagination(
                        currentPage = halamanAktif,
                        totalPages = totalPages,
                        onPageChange = { halamanBaru ->
                            halamanAktif = halamanBaru
                        }
                    )
                }
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