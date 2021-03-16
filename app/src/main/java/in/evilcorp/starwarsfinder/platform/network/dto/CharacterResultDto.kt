package `in`.evilcorp.starwarsfinder.platform.network.dto

import com.google.gson.annotations.SerializedName

data class CharacterResultDto(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") override val next: String? = null,
    @SerializedName("previous") val previous: Any? = null,
    @SerializedName("results") val results: List<CharacterBaseDto>
): StarWarsFetchableDto {

    companion object {
        const val KEY = "CHARACTER_RESULT_DTO"
    }
}