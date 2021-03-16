package `in`.evilcorp.starwarsfinder.domain.models

data class CharacterModel(
    val id: String,
    val name: String,
    val birthYear: String?,
    val height: Double?,
    val speciesId: String?
) : Comparable<CharacterModel> {

    override fun compareTo(other: CharacterModel): Int = name.compareTo(other.name)
}