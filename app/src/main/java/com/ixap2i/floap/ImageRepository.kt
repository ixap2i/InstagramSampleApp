package com.ixap2i.floap

import androidx.annotation.Nullable
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiJsonAdapterFactory
import java.lang.reflect.Type
import kotlin.collections.HashMap




// TODO repositoryにlivedataの実装
// TODO https://qiita.com/Tsutou/items/69a28ebbd69b69e51703
interface ImageRepository {
    suspend fun getUserImage(): Result<ImageResponse, ImageErrorResponse>
}

@JsonClass(generateAdapter = true)
class ImageResponceFactory: ImageResponceAdapterImpl() {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val rawType = Types.getRawType(type)
        if (!annotations.isEmpty()) return null
        return if (rawType == Data::class.java) {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            KotshiDataJsonAdapter(moshi).nullSafe()
        } else null
    }
    @ToJson
    fun toJson(writer: JsonWriter, value: Data, stringAdapter: JsonAdapter<Data>) {
        stringAdapter.toJson(writer, value)
    }
    @FromJson
    fun fromJson(json: String) {
        return // Stringを解析
    }
}

@JsonClass(generateAdapter = true)
abstract class ImageResponceAdapterImpl: JsonAdapter.Factory

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

@KotshiJsonAdapterFactory
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
) {
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
