package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import `in`.evilcorp.starwarsfinder.ui.characterlist.models.CharacterListViewItem

interface UiMapper {

    fun mapToViewListModel(domainItem: CharacterModel): CharacterListViewItem

    fun mapToViewDetails(
        character: CharacterModel,
        characterExtra: CharacterExtraModel?
    ): CharacterDetailsViewItem
}