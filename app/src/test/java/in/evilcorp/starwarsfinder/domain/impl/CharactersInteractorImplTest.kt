package `in`.evilcorp.starwarsfinder.domain.impl

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.CharactersRepository
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class CharactersInteractorImplTest : BaseRxTest() {

    private val repository: CharactersRepository = mock()

    private lateinit var interactor: CharactersInteractorImpl

    @Before
    fun setup() {
        interactor = CharactersInteractorImpl(repository)
    }

    @Test
    fun `get characters list`() {
        val characters = listOf(mock<CharacterModel>())

        whenever(repository.observeCharactersList()).thenReturn(Observable.just(characters))

        interactor.observeCharactersList()
            .test()
            .assertValue(characters)
    }

    @Test
    fun `get characters list WHEN empty`() {
        whenever(repository.observeCharactersList()).thenReturn(Observable.just(emptyList()))

        interactor.observeCharactersList()
            .test()
            .assertValue(emptyList())
    }

    @Test
    fun `get single character`() {
        val characterId = "characterId"
        val character = mock<CharacterModel>()

        whenever(repository.getCharacter(characterId)).thenReturn(Single.just(character))

        interactor.getBasicCharacterInfo(characterId)
            .test()
            .assertValue(character)
    }

    @Test
    fun `get single character WHEN upstream error`() {
        val characterId = "characterId"
        val error = IllegalStateException()

        whenever(repository.getCharacter(characterId)).thenReturn(Single.error(error))

        interactor.getBasicCharacterInfo(characterId)
            .test()
            .assertError(error)
    }

    @Test
    fun `get character extra`() {
        val characterId = "characterId"
        val characterExtra = mock<CharacterExtraModel>()

        whenever(repository.getCharacterExtra(characterId)).thenReturn(Maybe.just(characterExtra))

        interactor.getExtraCharacterInfo(characterId)
            .test()
            .assertValue(characterExtra)
    }

    @Test
    fun `get character extra WHEN not found`() {
        val characterId = "characterId"

        whenever(repository.getCharacterExtra(characterId)).thenReturn(Maybe.empty())

        interactor.getExtraCharacterInfo(characterId)
            .test()
            .assertNoValues()
    }

    @Test
    fun `handle characters refresh`() {
        whenever(repository.refreshCharacters()).thenReturn(Completable.complete())

        interactor.forceUpdateCharacters()
            .test()
            .assertComplete()
    }
}