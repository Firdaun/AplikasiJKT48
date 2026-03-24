package com.example.aplikasijkt48.network

import com.google.gson.annotations.SerializedName

// 1. Tangkapan Utama (Bungkus terluar JSON)
data class GalleryResponse(
    val data: List<PhotoItem>,
    val paging: PagingInfo? // Nullable jaga-jaga kalau error
)

// 2. Tangkapan Data Foto
data class PhotoItem(
    val id: Int,
    val srcUrl: String,
    val fileId: String,
    val caption: String?, // Tanda ? artinya bisa null/kosong
    val postUrl: String?,
    val mediaType: String,
    val postedAt: String,
    val savedAt: String,
    val source: String,
    val memberId: Int,
    val member: MemberInfo?
)

// 3. Tangkapan Info Member di dalam Foto
data class MemberInfo(
    val id: Int,
    val name: String,
    val nickname: String
)

// 4. Tangkapan Paging (Yang barusan kamu kirim)
data class PagingInfo(
    val page: Int,
    @SerializedName("total_item") val totalItem: Int,
    @SerializedName("total_page") val totalPage: Int
)