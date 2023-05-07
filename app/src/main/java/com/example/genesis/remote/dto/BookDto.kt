package com.example.genesis.remote.dto

import com.google.gson.annotations.SerializedName


data class BookList(
    @SerializedName("books") val bookList: List<BookDto?>?,
)

data class BookDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("author") val author: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("cover_url") val coverUrl: String,
    @SerializedName("views") val views: String,
    @SerializedName("likes") val likes: String,
    @SerializedName("quotes") val quotes: String
)

data class SlidesList(
    @SerializedName("top_banner_slides") val topBannerSlides: List<SlidesDto?>?,
)

data class SlidesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("book_id") val bookId: String,
    @SerializedName("cover") val cover: String,
)
data class YouWillLikeDto(
    @SerializedName("you_will_like_section") val topBannerSlides: List<Int>,
)