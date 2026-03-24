package com.example.aplikasijkt48.network

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {
    // State persis kayak di React: isLoading, data, errorMessage
    var fotoList by mutableStateOf<List<PhotoItem>>(emptyList())
    var pagingInfo by mutableStateOf<PagingInfo?>(null)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Fungsi utama buat narik data
    fun fetchPhotos(
        page: Int = 1,
        size: Int = 8,
        source: String? = null,
        nickname: String? = null,
        mode: String? = null,
        search: String? = null,
        postUrl: String? = null
    ) {
        // viewModelScope ini buat ngoding Asynchronous (pengganti async/await JS)
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // Nembak API pakai mesin Retrofit tadi
                val response = ApiClient.instance.getPublicPhotos(
                    page = page,
                    size = size,
                    source = if (source == "all" || source == "") null else source,
                    nickname = if (nickname == "") null else nickname,
                    mode = mode,
                    search = if (search == "") null else search,
                    postUrl = if (postUrl == "") null else postUrl
                )

                // Kalau berhasil, simpan datanya ke State
                fotoList = response.data
                pagingInfo = response.paging

            } catch (e: Exception) {
                // Kalau gagal (laptop mati, wifi putus, dll)
                errorMessage = e.message
                Log.e("API_ERROR", "Gagal narik data: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}