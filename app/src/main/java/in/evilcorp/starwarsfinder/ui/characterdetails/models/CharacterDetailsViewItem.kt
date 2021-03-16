package `in`.evilcorp.starwarsfinder.ui.characterdetails.models

data class CharacterDetailsViewItem(
    val id: String,
    val name: String,
    val birthYear: String?,
    val heightCm: Double?,
    val heightInch: Double?,
    val raceName: String?,
    val language: String?,
    val homeworld: String?,
    val population: Long?,
    val films: List<FilmViewItem> = emptyList()
)