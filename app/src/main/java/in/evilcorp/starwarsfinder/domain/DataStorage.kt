package `in`.evilcorp.starwarsfinder.domain

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DataStorage : DataProvider {

    fun updateCharacters(characters: List<CharacterModel>): Completable

    fun updateCharacterExtra(extra: CharacterExtraModel): Completable

    fun getCharacter(characterId: String): Single<CharacterModel>

}