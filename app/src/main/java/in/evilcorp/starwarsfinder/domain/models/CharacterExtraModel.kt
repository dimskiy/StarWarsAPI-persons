package `in`.evilcorp.starwarsfinder.domain.models

data class CharacterExtraModel(
    val id: String,
    val raceName: String?,
    val language: String?,
    val homeWorld: String?,
    val population: Long?,
    val films: List<FilmModel>
)