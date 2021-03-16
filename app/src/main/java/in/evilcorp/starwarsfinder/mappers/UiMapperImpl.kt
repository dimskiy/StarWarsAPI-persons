package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.domain.models.FilmModel
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.FilmViewItem
import `in`.evilcorp.starwarsfinder.ui.characterlist.models.CharacterListViewItem
import javax.inject.Inject

class UiMapperImpl @Inject constructor() : UiMapper {

    override fun mapToViewListModel(domainItem: CharacterModel) = CharacterListViewItem(
        id = domainItem.id,
        name = domainItem.name,
        birthYear = domainItem.birthYear,
        height = domainItem.height
    )

    override fun mapToViewDetails(
        character: CharacterModel,
        characterExtra: CharacterExtraModel?
    ) = CharacterDetailsViewItem(
        id = character.id,
        name = character.name,
        birthYear = character.birthYear,
        heightCm = character.height,
        heightInch = character.height?.let { it / INCH_CM_RATIO },
        raceName = characterExtra?.raceName,
        language = characterExtra?.language,
        homeworld = characterExtra?.homeWorld,
        population = characterExtra?.population,
        films = characterExtra?.films
            ?.map(this::mapToFilmViewItem)
            .orEmpty()
    )

    private fun mapToFilmViewItem(filmModel: FilmModel): FilmViewItem = FilmViewItem(
        id = filmModel.id,
        title = filmModel.title,
        episodeNum = filmModel.episodeNumber,
        description = filmModel.description
    )

    companion object {
        const val INCH_CM_RATIO = 2.54
    }
}