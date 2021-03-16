package `in`.evilcorp.starwarsfinder.platform.network.dto.person.species

import `in`.evilcorp.starwarsfinder.platform.network.dto.StarWarsFetchableDto
import com.google.gson.annotations.SerializedName

data class HomeworldDto(
    @SerializedName("url") val url: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("population") val population: String? = null
) : StarWarsFetchableDto {

    override val next: String? = null

    companion object {
        val EMPTY = HomeworldDto()
    }
}