package `in`.evilcorp.starwarsfinder.domain

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CharactersInteractor {

    fun observeCharactersList(): Observable<List<CharacterModel>>

    fun getBasicCharacterInfo(characterId: String): Single<CharacterModel>

    fun getExtraCharacterInfo(characterId: String): Maybe<CharacterExtraModel>

    fun forceUpdateCharacters(): Completable

}