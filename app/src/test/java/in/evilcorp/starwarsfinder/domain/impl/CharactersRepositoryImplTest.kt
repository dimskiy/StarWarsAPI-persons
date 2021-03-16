package `in`.evilcorp.starwarsfinder.domain.impl

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.DataProvider
import `in`.evilcorp.starwarsfinder.domain.DataStorage
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest : BaseRxTest() {

    private val networkProvider: DataProvider = mock()
    private val localStorage: DataStorage = mock()

    private lateinit var repository: CharactersRepositoryImpl

    @Before
    fun setup() {
        repository = CharactersRepositoryImpl(networkProvider, localStorage)
    }

    @Test
    fun `get character list`() {
        val characters = listOf(mock<CharacterModel>())
        whenever(localStorage.observeCharacters()).thenReturn(Observable.just(characters))

        repository.observeCharactersList()
            .test()
            .assertValue(characters)
    }

    @Test
    fun `get character list WHEN upstream error`() {
        val error = IllegalStateException()
        whenever(localStorage.observeCharacters()).thenReturn(Observable.error(error))

        repository.observeCharactersList()
            .test()
            .assertError(error)
    }

    @Test
    fun `get character extra`() {
        val characterId = "characterId"
        val specieId = "specieId"
        val extraModel = mock<CharacterExtraModel>()
        val character = mock<CharacterModel>()

        whenever(character.speciesId).thenReturn(specieId)
        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.just(character))
        whenever(localStorage.getCharacterExtra(specieId)).thenReturn(Maybe.just(extraModel))
        whenever(networkProvider.getCharacterExtra(specieId)).thenReturn(Maybe.empty())

        repository.getCharacterExtra(characterId)
            .test()
            .assertValue(extraModel)
    }

    @Test
    fun `get character extra WHEN specieId empty`() {
        val characterId = "characterId"
        val character = mock<CharacterModel>()

        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.just(character))

        repository.getCharacterExtra(characterId)
            .test()
            .assertNoValues()
            .assertNoErrors()
    }

    @Test
    fun `get character extra WHEN character storage empty`() {
        val characterId = "characterId"
        val error = IllegalStateException()

        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.error(error))

        repository.getCharacterExtra(characterId)
            .test()
            .assertError(error)
    }

    @Test
    fun `get character extra WHEN extra storage empty`() {
        val characterId = "characterId"
        val specieId = "specieId"
        val extraModel = mock<CharacterExtraModel>()
        val character = mock<CharacterModel>()

        whenever(character.speciesId).thenReturn(specieId)
        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.just(character))
        whenever(localStorage.getCharacterExtra(specieId)).thenReturn(Maybe.empty())
        whenever(networkProvider.getCharacterExtra(specieId)).thenReturn(Maybe.just(extraModel))
        whenever(localStorage.updateCharacterExtra(extraModel)).thenReturn(Completable.complete())

        repository.getCharacterExtra(characterId)
            .test()
            .assertValue(extraModel)

        verify(localStorage).updateCharacterExtra(extraModel)
    }

    @Test
    fun `get single character`() {
        val characterId = "characterId"
        val character = mock<CharacterModel>()

        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.just(character))

        repository.getCharacter(characterId)
            .test()
            .assertValue(character)
    }

    @Test
    fun `get single character WHEN not found`() {
        val characterId = "characterId"
        val error = IllegalStateException()

        whenever(localStorage.getCharacter(characterId)).thenReturn(Single.error(error))

        repository.getCharacter(characterId)
            .test()
            .assertError(error)
    }

    @Test
    fun `handle character refresh`() {
        val characters = listOf(mock<CharacterModel>())

        whenever(networkProvider.observeCharacters()).thenReturn(Observable.just(characters))
        whenever(localStorage.updateCharacters(characters)).thenReturn(Completable.complete())

        repository.refreshCharacters()
            .test()
            .assertComplete()
    }

}