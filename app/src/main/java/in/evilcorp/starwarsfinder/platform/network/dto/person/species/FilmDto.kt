package `in`.evilcorp.starwarsfinder.platform.network.dto.person.species

import `in`.evilcorp.starwarsfinder.platform.network.dto.StarWarsFetchableDto
import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("episode_id") val episodeNumber: Int? = null,
    @SerializedName("opening_crawl") val description: String? = null
) : StarWarsFetchableDto {

    override val next: String? = null

    companion object {
        val EMPTY = FilmDto
    }

}