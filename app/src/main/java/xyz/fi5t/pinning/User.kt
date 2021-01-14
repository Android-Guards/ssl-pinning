package xyz.fi5t.pinning

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val login: String,

    @Json(name = "html_url")
    val url: String
)
