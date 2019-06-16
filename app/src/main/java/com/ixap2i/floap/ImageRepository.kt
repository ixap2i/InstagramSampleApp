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
class ImageRepository: ViewModel() {
    fun getHello(): String = "Ktor & Koin"

}

@KotshiJsonAdapterFactory
abstract class ImageResponceImpl(
    @field:Json(name = "pagination") var pagination: Pagination,
    @field:Json(name = "cooking_records") var cookingRecords: List<ImageRecord>
): ImageResponse {
    companion object {
        val INSTANCE: Companion = ImageResponceImpl
        @ToJson
        fun toJson(writer: JsonWriter, value: ImageRecord, stringAdapter: JsonAdapter<ImageRecord>) {
            stringAdapter.toJson(writer, value)
        }
    }
}

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

data class ImageRecord(
    val image_url: String?,
    val comment: String,
    @Json(name = "recipe_type") val recipeType: String?,
    val recorded_at: String?
)
