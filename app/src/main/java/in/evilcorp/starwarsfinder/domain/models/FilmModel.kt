package `in`.evilcorp.starwarsfinder.domain.models

data class FilmModel(
    val id: String,
    val title: String,
    val episodeNumber: Int?,
    val description: String?
)