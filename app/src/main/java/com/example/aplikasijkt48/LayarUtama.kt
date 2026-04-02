package com.example.aplikasijkt48

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasijkt48.components.FloatingControlBar
import com.example.aplikasijkt48.components.GalleryGrid
import com.example.aplikasijkt48.components.GalleryItem
import com.example.aplikasijkt48.components.Lightbox
import com.example.aplikasijkt48.components.Pagination
import com.example.aplikasijkt48.components.SearchResultsInfo
import com.example.aplikasijkt48.components.StoryCarousel
import com.example.aplikasijkt48.navbar.TopNavbar
import com.example.aplikasijkt48.network.ApiConfig
import com.example.aplikasijkt48.network.GalleryViewModel
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: GalleryViewModel = viewModel()
) {
    var activeMemberName by remember { mutableStateOf("") }
    var viewMode by remember { mutableStateOf("album") }
    var activePlatform by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }
    var currentPage by remember { mutableIntStateOf(1) }
    var postUrl by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val tinggiPagination = 120.dp
    val tinggiPaginationPx = with(LocalDensity.current) { tinggiPagination.roundToPx().toFloat() }

    val offsetAnimatable = remember { Animatable(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                if (delta < -10f && offsetAnimatable.targetValue != tinggiPaginationPx) {
                    coroutineScope.launch {
                        offsetAnimatable.animateTo(
                            targetValue = tinggiPaginationPx,
                            animationSpec = tween(durationMillis = 250)
                        )
                    }
                }
                else if (delta > 1f && offsetAnimatable.targetValue != 0f) {
                    coroutineScope.launch {
                        offsetAnimatable.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 250)
                        )
                    }
                }
                return Offset.Zero
            }
        }
    }

    var isRefreshing by remember { mutableStateOf(false) }
    var lightboxItem by remember { mutableStateOf<GalleryItem?>(null) }
    val pullRefreshState = rememberPullToRefreshState()

    val backgroundBlur by animateDpAsState(
        targetValue = if (lightboxItem != null) 20.dp else 0.dp,
        label = "backgroundBlur"
    )

    val galleryItems = viewModel.fotoList.map { apiData ->
        GalleryItem(
            platform = apiData.source,
            isVideo = apiData.mediaType == "VIDEO" || apiData.srcUrl.endsWith(".mp4"),
            imageUrl = "${ApiConfig.BASE_URL}${apiData.srcUrl}",
            caption = apiData.caption ?: "Tanpa Caption",
            date = apiData.postedAt.take(10),
            member = apiData.member?.nickname ?: "JKT48",
            postUrl = apiData.postUrl ?: ""
        )
    }

    val totalItemsCount = viewModel.pagingInfo?.totalItem ?: 0
    val totalPagesCount = viewModel.pagingInfo?.totalPage ?: 1

    fun fetchGalleryData(
        targetPage: Int = currentPage,
        targetMode: String = viewMode,
        targetNickname: String = activeMemberName,
        targetUrl: String = postUrl,
        targetPlatform: String = activePlatform,
        isRefresh: Boolean = false
    ) {
        viewModel.fetchPhotos(
            page = targetPage,
            size = if (targetMode == "album") 8 else 27,
            source = targetPlatform,
            nickname = targetNickname,
            mode = targetMode,
            postUrl = targetUrl,
            forceRefresh = isRefresh
        )
        viewModel.fetchMemberGlobalAlbumCount(targetNickname)
    }

    LaunchedEffect(Unit) {
        fetchGalleryData()
    }

    LaunchedEffect(viewModel.isLoading) {
        if (!viewModel.isLoading) {
            isRefreshing = false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = backgroundBlur)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color(0xFF07070F),
                topBar = {
                    TopNavbar()
                }
            ) { innerPadding ->

                Box(modifier = Modifier.fillMaxSize().nestedScroll(nestedScrollConnection)) {
                    BackgroundDecoration()

                    PullToRefreshBox(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        state = pullRefreshState,
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true

                            fetchGalleryData(isRefresh = true)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 13.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            StoryCarousel(
                                modifier = Modifier.padding(top = 20.dp),
                                activeMember = activeMemberName,
                                onSelectMember = { memberName ->
                                    fetchGalleryData(
                                        targetNickname = memberName,
                                        targetPage = 1
                                    )
                                    activeMemberName = memberName
                                    searchQuery = ""
                                    currentPage = 1
                                }
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            FloatingControlBar(
                                viewMode = viewMode,
                                onViewModeChange = {
                                    fetchGalleryData(
                                        targetPage = 1,
                                        targetMode = it,
                                        targetUrl = ""
                                    )
                                    viewMode = it
                                    currentPage = 1
                                },
                                activePlatform = activePlatform,
                                onPlatformChange = {
                                    fetchGalleryData(targetPlatform = it, targetPage = 1)
                                    activePlatform = it
                                    currentPage = 1
                                },
                                searchQuery = searchQuery,
                                onSearchChange = {
                                    searchQuery = it
                                },
                                onSearchExecute = { kataKunci ->
                                    if (kataKunci.isNotBlank()) {
                                        activeMemberName = kataKunci.lowercase().trim()
                                        fetchGalleryData(
                                            targetNickname = activeMemberName,
                                            targetPage = 1
                                        )
                                        currentPage = 1
                                    }
                                },
                                onClear = {
                                    fetchGalleryData(
                                        targetNickname = "",
                                        targetPage = 1
                                    )
                                    searchQuery = ""
                                    activeMemberName = ""
                                    currentPage = 1
                                }
                            )

                            Spacer(modifier = Modifier.height(13.dp))

                            SearchResultsInfo(
                                nickname = activeMemberName,
                                viewMode = viewMode,
                                totalItem = totalItemsCount,
                                postUrl = postUrl,
                                globalAlbumCount = viewModel.globalAlbumCount,
                                onShowAllClick = {
                                    fetchGalleryData(
                                        targetNickname = "",
                                        targetUrl = "",
                                        targetPage = 1
                                    )
                                    activeMemberName = ""
                                    searchQuery = ""
                                    postUrl = ""
                                    currentPage = 1
                                },
                                onShowMemberPhotosClick = {
                                    fetchGalleryData(
                                        targetPage = 1,
                                        targetMode = "photo",
                                        targetUrl = ""
                                    )
                                    viewMode = "photo"
                                    postUrl = ""
                                    currentPage = 1
                                }
                            )

                            Spacer(modifier = Modifier.height(13.dp))

                            if (viewModel.isLoading && viewModel.fotoList.isEmpty()) {
                                Box(
                                    modifier = Modifier.height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color(0xFFEE1D52))
                                }
                            } else if (viewModel.errorMessage != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .height(200.dp), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Error: ${viewModel.errorMessage}",
                                        color = Color.Red
                                    )
                                }
                            } else {
                                GalleryGrid(
                                    viewMode = viewMode,
                                    items = galleryItems,
                                    onItemClick = { onClick ->
                                        if (viewMode == "album") {
                                            val indexData = galleryItems.indexOf(onClick)
                                            if (indexData != -1) {
                                                val targetPostUrl =
                                                    viewModel.fotoList[indexData].postUrl ?: ""
                                                val targetNickname = onClick.member.lowercase()
                                                fetchGalleryData(
                                                    targetPage = 1,
                                                    targetMode = "photo",
                                                    targetNickname = targetNickname,
                                                    targetUrl = targetPostUrl
                                                )

                                                postUrl = targetPostUrl
                                                viewMode = "photo"
                                                currentPage = 1
                                                activeMemberName = onClick.member.lowercase()
                                            }
                                        } else {
                                            lightboxItem = onClick
                                        }
                                    }
                                )
                            }
                        }

                    }
                    if (totalPagesCount > 1) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .navigationBarsPadding()
                                    .graphicsLayer {
                                        translationY = offsetAnimatable.value
                                    }
                                    .background(Color(0xFF07070F))
                            ) {
                                Pagination(
                                    currentPage = currentPage,
                                    totalPages = totalPagesCount,
                                    onPageChange = { halamanBaru ->
                                        fetchGalleryData(targetPage = halamanBaru)

                                        currentPage = halamanBaru
                                    }
                                )
                            }
                    }

                }

            }
        }
        AnimatedVisibility(
            visible = lightboxItem != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            lightboxItem?.let { item ->
                Lightbox(
                    item = item,
                    allItems = galleryItems,
                    onClose = { lightboxItem = null },
                    onNavigate = { itemBaru -> lightboxItem = itemBaru }
                )
            }
        }

    }
}

@Composable
fun BackgroundDecoration() {
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
        MainScreen()
    }
}