package `in`.evilcorp.starwarsfinder.platform.database

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.domain.models.FilmModel
import `in`.evilcorp.starwarsfinder.mappers.DomainMapper
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.CharacterExtraWithFilms
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class LocalDataStorageTest :BaseRxTest() {
    private val dao: StarWarsDao = mock()
    private val mapper: DomainMapper = mock()

    private lateinit var storage: LocalDataStorage

    @Before
    fun setup() {
        storage = LocalDataStorage(dao, mapper)
    }

    @Test
    fun `update characters`() {
        val character = mock<CharacterModel>()
        val characterEntity = mock<CharacterEntity>()

        whenever(mapper.mapToEntity(character)).thenReturn(characterEntity)
        whenever(dao.putCharacters(listOf(characterEntity))).thenReturn(Completable.complete())

        storage.updateCharacters(listOf(character))
            .test()
            .assertComplete()
    }

    @Test
    fun `observe characters`() {
        val character = mock<CharacterModel>()
        val characterEntity = mock<CharacterEntity>()

        whenever(dao.selectAllCharacters()).thenReturn(
            Observable.just(listOf(characterEntity))
        )
        whenever(mapper.mapToDomain(characterEntity)).thenReturn(character)

        storage.observeCharacters()
            .test()
            .assertValue(listOf(character))
    }

//    @Test
//    fun `observe characters WHEN empty`() {
//
//    }

    @Test
    fun `get single character`() {
        val id = "id"
        val character = mock<CharacterModel>()
        val characterEntity = mock<CharacterEntity>()

        whenever(dao.selectCharacter(id)).thenReturn(Single.just(characterEntity))
        whenever(mapper.mapToDomain(characterEntity)).thenReturn(character)

        storage.getCharacter(id)
            .test()
            .assertValue(character)
    }

    @Test
    fun `get single character WHEN not existing`() {
        val error = IllegalStateException()

        whenever(dao.selectCharacter(any())).thenReturn(Single.error(error))

        storage.getCharacter("")
            .test()
            .assertError(error)
    }

    @Test
    fun `get single extra`() {
        val id = "id"
        val extra = mock<CharacterExtraModel>()
        val extraEntity = mock<CharacterExtraWithFilms>()

        whenever(dao.selectCharacterExtra(id)).thenReturn(Maybe.just(extraEntity))
        whenever(mapper.mapToDomain(extraEntity)).thenReturn(extra)

        storage.getCharacterExtra(id)
            .test()
            .assertValue(extra)
    }

    @Test
    fun `get single extra WHEN not existing`() {
        whenever(dao.selectCharacterExtra(any())).thenReturn(Maybe.empty())

        storage.getCharacterExtra("id")
            .test()
            .assertNoValues()
            .assertComplete()
    }

    @Test
    fun `update character extra`() {
        val extra = mock<CharacterExtraModel>()
        val extraEntity = mock<CharacterExtraEntity>()
        val film = mock<FilmModel>()
        val filmEntity = mock<FilmEntity>()

        whenever(extra.films).thenReturn(listOf(film))
        whenever(mapper.mapToEntity(extra)).thenReturn(extraEntity)
        whenever(mapper.mapToEntity(film)).thenReturn(filmEntity)

        storage.updateCharacterExtra(extra)
            .test()
            .assertComplete()
    }

    @Test
    fun `update character extra WHEN no films`() {
        val extra = mock<CharacterExtraModel>()
        val extraEntity = mock<CharacterExtraEntity>()

        whenever(extra.films).thenReturn(emptyList())
        whenever(mapper.mapToEntity(extra)).thenReturn(extraEntity)

        storage.updateCharacterExtra(extra)
            .test()
            .assertComplete()
    }
}