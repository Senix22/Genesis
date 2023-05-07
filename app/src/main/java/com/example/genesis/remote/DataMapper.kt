package com.example.genesis.remote

import com.example.genesis.remote.dto.*
import com.example.genesis.util.safeParseJson
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.gson.Gson
import javax.inject.Inject

const val EMPTY_STRING = ""

class DataMapper @Inject constructor() {
    private val gson: Gson = Gson()
    operator fun invoke(
        map: Map<String, FirebaseRemoteConfigValue>
    ): RCDataModel {

        val teamMembersJson = map[RCKey.jsonData]?.asString()

        val book = gson.safeParseJson<BookList>(teamMembersJson)?.let {
            mapBook(it.bookList)
        }
        val slidesJson = map[RCKey.topBannerSlides]?.asString()

        val slides = gson.safeParseJson<SlidesList>(teamMembersJson)?.let {
            mapSlides(it.topBannerSlides)
        }
        val youWillLike = map[RCKey.like_section]?.asString()

        val likeSection = gson.safeParseJson<YouWillLikeDto>(teamMembersJson)?.let {
            mapYouWillLike(it.topBannerSlides)
        }



        return RCDataModel(
            bookModel = book.orEmpty(),
            slides = slides.orEmpty(),
            youWillLiceSection = likeSection.orEmpty()
        )
    }


    private fun mapBook(
        dto: List<BookDto?>?
    ): List<BookDataModel> {
        dto ?: return emptyList()

        return dto.mapNotNull {
            BookDataModel(
                id = it?.id ?: 0,
                name = it?.name.orEmpty(),
                author = it?.author.orEmpty(),
                summary = it?.summary.orEmpty(),
                genre = it?.genre.orEmpty(),
                coverUrl = it?.coverUrl.orEmpty(),
                views = it?.views.toString(),
                likes = it?.likes.toString(),
                quotes = it?.quotes.toString(),
            )
        }
    }

    private fun mapSlides(
        dto: List<SlidesDto?>?
    ): List<SlidesDataModel> {
        dto ?: return emptyList()

        return dto.mapNotNull {
            SlidesDataModel(
                id = it?.id ?: 0,
                bookId = it?.bookId.orEmpty(),
                cover = it?.cover.orEmpty()
            )
        }
    }

    private fun mapYouWillLike(
        dto: List<Int>
    ): List<YouWillLikeModel> {
        dto ?: return emptyList()

        return dto.mapNotNull {
            YouWillLikeModel(it)
        }
    }


}


typealias StringMapper = (String) -> String?

data class BookDataModel(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: String,
    val coverUrl: String,
    val views: String,
    val likes: String,
    val quotes: String
)

data class SlidesDataModel(
    val id: Int,
    val bookId: String,
    val cover: String
)

data class YouWillLikeModel(
    val id: Int,
)

private object RCKey {
    const val jsonData = "json_data"
    const val topBannerSlides = "top_banner_slides"
    const val details_carousel = "details_carousel"
    const val like_section = "you_will_like_section"

}


data class RCDataModel constructor(
    val bookModel: List<BookDataModel>,
    val slides: List<SlidesDataModel>,
    val youWillLiceSection: List<YouWillLikeModel>,
)


