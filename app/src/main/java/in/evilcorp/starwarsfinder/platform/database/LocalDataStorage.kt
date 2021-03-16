package `in`.evilcorp.starwarsfinder.platform.database

import `in`.evilcorp.starwarsfinder.domain.DataStorage
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.DomainMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalDataStorage @Inject constructor(
    private val dao: StarWarsDao,
    private val mapper: DomainMapper
) : DataStorage {

    override fun updateCharacters(characters: List<CharacterModel>): Completable =
        dao.putCharacters(
            characters.map(mapper::mapToEntity)
        )

    override fun observeCharacters(): Observable<List<CharacterModel>> =
        dao.selectAllCharacters()
            .map { characters ->
                characters.map(mapper::mapToDomain)
            }

    override fun getCharacter(characterId: String): Single<CharacterModel> =
        dao.selectCharacter(characterId).map(mapper::mapToDomain)

    override fun getCharacterExtra(specieId: String): Maybe<CharacterExtraModel> =
        dao.selectCharacterExtra(specieId).map(mapper::mapToDomain)

    override fun updateCharacterExtra(extra: CharacterExtraModel): Completable {
        val extraEntityItem = mapper.mapToEntity(extra)
        val filmEntityItems = extra.films.map(mapper::mapToEntity)

        return Completable.fromAction {
            dao.updateExtraWithFilmsTransaction(extraEntityItem, filmEntityItems)
        }
    }
}