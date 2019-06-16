package com.ixap2i.floap

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiJsonAdapterFactory

// TODO repositoryにlivedataの実装
// TODO https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
class ImageRepository: ViewModel() {
    fun getHello(): String = "Ktor & Koin"

}

@KotshiJsonAdapterFactory
abstract class ImageResponceImpl(
    @field:Json(name = "pagination") var pagination: Pagination,
    @field:Json(name = "cooking_records") var cookingRecords: List<ImageRecord>
) : ImageResponse

@JsonSerializable
data class Pagination(
    val total: String,
    val offet: String,
    val limit: String
)

@KotshiJsonAdapterFactory
data class Album(
    @field:Json(name = "pagination") var pagination: Pagination,
    @field:Json(name = "cooking_records") var cookingRecords: List<ImageRecord>
) {

    companion object {
        val INSTANCE: Companion = Album
        @ToJson
        fun toJson(writer: JsonWriter, value: ImageRecord, stringAdapter: JsonAdapter<ImageRecord>) {
            stringAdapter.toJson(writer, value)
        }
    }
}

data class ImageRecord(
    val image_url: String?,
    val comment: String,
    @Json(name = "recipe_type") val recipeType: String?,
    val recorded_at: String?
)
