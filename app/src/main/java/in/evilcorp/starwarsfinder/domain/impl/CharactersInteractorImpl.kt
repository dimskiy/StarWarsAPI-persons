package `in`.evilcorp.starwarsfinder.domain.impl

import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.domain.CharactersRepository
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CharactersInteractorImpl @Inject constructor(
    private val repository: CharactersRepository
): CharactersInteractor {

    override fun observeCharactersList(): Observable<List<CharacterModel>> =
        repository.observeCharactersList()

    override fun getBasicCharacterInfo(characterId: String): Single<CharacterModel> =
        repository.getCharacter(characterId)

    override fun getExtraCharacterInfo(characterId: String): Maybe<CharacterExtraModel> =
        repository.getCharacterExtra(characterId)

    override fun forceUpdateCharacters(): Completable = repository.refreshCharacters()
}