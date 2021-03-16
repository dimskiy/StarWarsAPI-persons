package `in`.evilcorp.starwarsfinder.platform.network.dto

import com.google.gson.annotations.SerializedName

data class CharacterBaseDto(
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth_year") val birthYear: String? = null,
    @SerializedName("height") val height: String? = null,
    @SerializedName("species") val speciesUrls: List<String>? = null
)