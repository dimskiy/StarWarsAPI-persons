package `in`.evilcorp.starwarsfinder.ui.characterlist

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.ui.characterlist.models.CharacterListViewItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

class CharacterListViewModelTest : BaseRxTest() {

    private val interactor: CharactersInteractor = mock()
    private val mapper: UiMapper = mock()

    private lateinit var viewModel: CharacterListViewModel

    @Before
    fun setup() {
        viewModel = CharacterListViewModel(interactor, mapper)
    }

    @Test
    fun `get characters list`() {
        val characterModel = mock<CharacterModel>()
        val characterViewItem = mock<CharacterListViewItem>()

        whenever(interactor.observeCharactersList()).thenReturn(
            Observable.just(listOf(characterModel))
        )
        whenever(mapper.mapToViewListModel(characterModel)).thenReturn(characterViewItem)

        viewModel.observeCharacters()
            .test()
            .assertValue(listOf(characterViewItem))
    }

    @Test
    fun `get characters list WHEN empty`() {
        whenever(interactor.observeCharactersList()).thenReturn(
            Observable.just(emptyList())
        )
        whenever(interactor.forceUpdateCharacters()).thenReturn(Completable.complete())

        viewModel.observeCharacters()
            .test()
            .assertValueCount(0)
            .assertComplete()

        verify(interactor).forceUpdateCharacters()
    }

    @Test
    fun `get characters list WHEN upstream error`() {
        val error = IllegalStateException()
        whenever(interactor.observeCharactersList()).thenReturn(
            Observable.error(error)
        )

        viewModel.observeCharacters()
            .test()
            .assertError(error)
    }
}