package `in`.evilcorp.starwarsfinder.domain

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CharactersRepository {

    fun observeCharactersList(): Observable<List<CharacterModel>>

    fun getCharacter(characterId: String): Single<CharacterModel>

    fun getCharacterExtra(characterId: String): Maybe<CharacterExtraModel>

    fun refreshCharacters(): Completable

}