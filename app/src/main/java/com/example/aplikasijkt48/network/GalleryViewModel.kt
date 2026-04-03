package com.example.aplikasijkt48.network

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class CacheEntry(
    val fotoList: List<PhotoItem>,
    val pagingInfo: PagingInfo?,
    val timestamp: Long
)

class GalleryViewModel : ViewModel() {
    var fotoList by mutableStateOf<List<PhotoItem>>(emptyList())
    var pagingInfo by mutableStateOf<PagingInfo?>(null)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var globalAlbumCount by mutableIntStateOf(0)
        private set
    var globalTotalMedia by mutableIntStateOf(0)
        private set

    private val cacheMap = mutableMapOf<String, CacheEntry>()
    private val cacheDuration = 15 * 60 * 1000L

    fun fetchPhotos(
        page: Int = 1,
        size: Int = 8,
        source: String? = null,
        nickname: String? = null,
        mode: String? = null,
        search: String? = null,
        postUrl: String? = null,
        forceRefresh: Boolean = false
    ) {
        val cacheKey =
            "page=$page|size=$size|src=$source|nick=$nickname|mode=$mode|search=$search|url=$postUrl"
        val currentTime = System.currentTimeMillis()

        val existingCache = cacheMap[cacheKey]
        if (!forceRefresh && existingCache != null && (currentTime - existingCache.timestamp < cacheDuration)) {
            Log.d(
                "API_CACHE",
                "Halaman/Filter ini sudah ada di RAM! Langsung tampilin tanpa nembak API. ($cacheKey)"
            )

            fotoList = existingCache.fotoList
            pagingInfo = existingCache.pagingInfo
            isLoading = false

            return
        }

        if (!forceRefresh) {
            fotoList = emptyList()
            isLoading = true
        }
        errorMessage = null

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = ApiClient.instance.getPublicPhotos(
                    page = page,
                    size = size,
                    source = if (source == "all" || source == "") null else source,
                    nickname = if (nickname == "") null else nickname,
                    mode = mode,
                    search = if (search == "") null else search,
                    postUrl = if (postUrl == "") null else postUrl
                )

                fotoList = response.data
                pagingInfo = response.paging

                cacheMap[cacheKey] = CacheEntry(
                    fotoList = response.data,
                    pagingInfo = response.paging,
                    timestamp = currentTime
                )
                Log.d("API_CACHE", "Berhasil nembak API. Data disimpan ke RAM untuk ($cacheKey)")

            } catch (e: Exception) {
                errorMessage = e.message
                Log.e("API_ERROR", "Gagal narik data: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchMemberGlobalAlbumCount(nickname: String?) {
        if (nickname.isNullOrEmpty()) {
            globalAlbumCount = 0
            return
        }
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getPublicPhotos(
                    page = 1,
                    size = 1,
                    source = null,
                    nickname = nickname,
                    mode = "album",
                    search = null,
                    postUrl = null
                )
                globalAlbumCount = response.paging?.totalItem ?: 0
                Log.d(
                    "API_INTEL",
                    "Intel berhasil mencatat $nickname punya $globalAlbumCount album"
                )
            } catch (e: Exception) {
                globalAlbumCount = 0
                Log.e("API_INTEL", "Intel gagal: ${e.message}")
            }
        }
    }

    fun fetchGlobalTotalMedia() {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getPublicPhotos(
                    page = 1,
                    size = 1,
                    source = null,
                    nickname = null,
                    mode = null,
                    search = null,
                    postUrl = null
                )
                globalTotalMedia = response.paging?.totalItem ?: 0
            } catch (e: Exception) {
                globalTotalMedia = 0
                Log.e("API_INTEL", "Gagal mengambil global total media: ${e.message}")
            }
        }
    }
}

