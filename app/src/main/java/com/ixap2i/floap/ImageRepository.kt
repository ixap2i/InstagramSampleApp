package com.ixap2i.floap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.*
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiJsonAdapterFactory

// TODO repositoryにlivedataの実装
// TODO https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
interface ImageRepository {
    suspend fun getUserImage(): Result<ImageResponceFactory, ImageErrorResponse>
}

@JsonSerializable
@JsonClass(generateAdapter = true)
data class ImageResponceFactory(
    @field:Json(name = "pagination") override val pagination: Pagination?,
    @field:Json(name = "data") override val data: List<Data>
): ImageResponse {
    @ToJson
    fun toJson(writer: JsonWriter, value: Data, stringAdapter: JsonAdapter<Data>) {
        stringAdapter.toJson(writer, value)
    }
    @FromJson
    fun fromJson(json: String) {
        return
    }
}

@KotshiJsonAdapterFactory
@JsonSerializable
data class Data(
    val caption: Caption?,
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
): ViewModel() {
    val getImages: MutableLiveData<Images> by lazy {
        MutableLiveData<Images>()
    }
    val getDatas: MutableLiveData<Data> by lazy {
        MutableLiveData<Data>()
    }
    companion object {
        val INSTANCE: Companion = Data
        @ToJson
        fun toJson(writer: JsonWriter, value: ImageRecord, stringAdapter: JsonAdapter<ImageRecord>) {
            stringAdapter.toJson(writer, value)
        }
    }
}

@JsonSerializable
data class Pagination(
    val total: String?,
    val offet: String?,
    val limit: String?
)

@JsonSerializable
data class Caption(
    val created_time: String?,
    val from: User?,
    val id: String?,
    val text: String?
)

@JsonSerializable
data class Images(
    @field:Json(name = "thumbnail") val thumbnail: Detail?,
    @field:Json(name = "low_resolution") val lowResolution: Detail?,
    @field:Json(name = "standard_resolution") val standardResolution: Detail?
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

@JsonSerializable
data class ImageRecord(
    val image_url: String?,
    val comment: String,
    @Json(name = "recipe_type") val recipeType: String?,
    val recorded_at: String?
)

@JsonSerializable
data class Detail(
    val width: String?,
    val height: String?,
    val url: String?
)
