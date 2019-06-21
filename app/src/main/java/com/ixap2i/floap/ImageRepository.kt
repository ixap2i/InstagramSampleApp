package com.ixap2i.floap

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiJsonAdapterFactory
import kotlin.collections.HashMap

// TODO repositoryにlivedataの実装
// TODO https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
interface ImageRepository {
    suspend fun getUserImage(): Result<ImageResponse, ImageErrorResponse>
}

@KotshiJsonAdapterFactory
abstract class ImageResponceImpl(
    @field:Json(name = "pagination") var pagination: Pagination,
    @field:Json(name = "data") var cookingRecords: Array<Data>
): ImageResponse {
    companion object {
        val INSTANCE: Companion = ImageResponceImpl
        @ToJson
        fun toJson(writer: JsonWriter, value: ImageRecord, stringAdapter: JsonAdapter<ImageRecord>) {
            stringAdapter.toJson(writer, value)
        }
    }
}


//@KotshiJsonAdapterFactory
//abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
//    companion object {
//        val INSTANCE = KotshiApplicationJsonAdapterFactory()
//    }
//}

@JsonSerializable
data class Data(
    val caption: Caption,
    val comments: Comments,
    val created_time: String,
    val filter: String,
    val id: String,
    val images: Images,
    val likes: Likes,
    val link: String,
    val location: String?,
    val tags: Array<String>,
    val type: String,
    val user: User,
    val user_has_liked: Boolean,
    val attribution: String?,
    val users_in_photo: Array<String>?
)

@JsonSerializable
data class Pagination(
    val total: String,
    val offet: String,
    val limit: String
)

@JsonSerializable
data class Caption(
    val id: String,
    val text: String,
    val from: List<User>,
    val created_time: String
)

@JsonSerializable
data class Images(
    // TODO　文字列数字の組み合わせが入ることへの対処
    @field:Json(name = "low_resolution") val lowResolution: HashMap<String, String>,
    @field:Json(name = "standard_resolution") val standardResolution: String,
    val thumbnail: String
)

@JsonSerializable
data class User(
    val id: String,
    val full_name: String,
    val profile_picture: String,
    val username: String
)

@JsonSerializable
data class Likes(
    val count: Int
)

@JsonSerializable
data class Comments(
    val count: Int
)

data class ImageRecord(
    val image_url: String?,
    val comment: String,
    @Json(name = "recipe_type") val recipeType: String?,
    val recorded_at: String?
)
