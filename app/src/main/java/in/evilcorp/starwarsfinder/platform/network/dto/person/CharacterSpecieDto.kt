package `in`.evilcorp.starwarsfinder.platform.network.dto.person

import `in`.evilcorp.starwarsfinder.platform.network.dto.StarWarsFetchableDto
import com.google.gson.annotations.SerializedName

data class CharacterSpecieDto(
    @SerializedName("url") val url: String,
    @SerializedName("name") val raceName: String? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("homeworld") val homeworldUrl: String? = null,
    @SerializedName("films") val filmUrls: List<String>? = null,
): StarWarsFetchableDto {

    override val next: String? = null

    companion object {
        const val KEY = "CHARACTER_SPECIE_DTO"
    }
}