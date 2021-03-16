package `in`.evilcorp.starwarsfinder.domain.impl

import `in`.evilcorp.starwarsfinder.domain.CharactersRepository
import `in`.evilcorp.starwarsfinder.domain.DataProvider
import `in`.evilcorp.starwarsfinder.domain.DataStorage
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val networkProvider: DataProvider,
    private val localStorage: DataStorage
) : CharactersRepository {

    override fun observeCharactersList(): Observable<List<CharacterModel>> =
        localStorage.observeCharacters()
            .subscribeOn(Schedulers.io())

    override fun getCharacterExtra(characterId: String): Maybe<CharacterExtraModel> =
        getCharacter(characterId)
            .subscribeOn(Schedulers.io())
            .flatMapMaybe { character ->
                val speciesId = character.speciesId
                if (speciesId?.isNotEmpty() == true) {
                    getCharacterExtraInternal(speciesId)
                } else {
                    Maybe.empty()
                }
            }

    override fun getCharacter(characterId: String): Single<CharacterModel> =
        localStorage.getCharacter(characterId)
            .subscribeOn(Schedulers.io())

    private fun getCharacterExtraInternal(specieId: String): Maybe<CharacterExtraModel> =
        localStorage.getCharacterExtra(specieId)
            .switchIfEmpty(
                networkProvider
                    .getCharacterExtra(specieId)
                    .withStorageUpdate()
            )

    private fun Maybe<CharacterExtraModel>.withStorageUpdate(): Maybe<CharacterExtraModel> =
        this.flatMap { extra ->
            localStorage.updateCharacterExtra(extra)
                .andThen(Maybe.just(extra))
        }

    override fun refreshCharacters(): Completable = networkProvider.observeCharacters()
        .subscribeOn(Schedulers.io())
        .flatMapCompletable(localStorage::updateCharacters)
}