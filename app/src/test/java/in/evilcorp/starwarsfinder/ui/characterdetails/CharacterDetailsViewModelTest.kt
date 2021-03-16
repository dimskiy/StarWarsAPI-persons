package `in`.evilcorp.starwarsfinder.ui.characterdetails

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.FilmViewItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class CharacterDetailsViewModelTest : BaseRxTest() {
    private val interactor: CharactersInteractor = mock()
    private val mapper: UiMapper = mock()

    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setup() {
        viewModel = CharacterDetailsViewModel(interactor, mapper)
    }

    @Test
    fun `character details`() {
        val id = "id"
        val basicModel = mock<CharacterModel>()
        val extraModel = mock<CharacterExtraModel>()
        val viewDetails = mock<CharacterDetailsViewItem>()

        whenever(interactor.getBasicCharacterInfo(id)).thenReturn(Single.just(basicModel))
        whenever(interactor.getExtraCharacterInfo(id)).thenReturn(Maybe.just(extraModel))
        whenever(mapper.mapToViewDetails(basicModel, extraModel)).thenReturn(viewDetails)
        whenever(mapper.mapToViewDetails(basicModel, null)).thenReturn(mock())

        viewModel.getCharacterDetails(id)
            .test()
            .assertValue(viewDetails)
    }

    @Test
    fun `character details when upstream error`() {
        val id = "id"
        val error = IllegalStateException()
        whenever(interactor.getBasicCharacterInfo(id)).thenReturn(Single.error(error))

        viewModel.getCharacterDetails(id)
            .test()
            .assertError(error)
    }

    @Test
    fun `character details WHEN extras not found`() {
        val id = "id"
        val basicModel = mock<CharacterModel>()
        val viewDetails = mock<CharacterDetailsViewItem>()

        whenever(interactor.getBasicCharacterInfo(id)).thenReturn(Single.just(basicModel))
        whenever(interactor.getExtraCharacterInfo(id)).thenReturn(Maybe.empty())
        whenever(mapper.mapToViewDetails(basicModel, null)).thenReturn(viewDetails)

        viewModel.getCharacterDetails(id)
            .test()
            .assertValue(viewDetails)
    }

    @Test
    fun `character details WHEN id empty`() {
        val id = ""
        val error = IllegalStateException()

        whenever(interactor.getBasicCharacterInfo(id)).thenReturn(Single.error(error))

        viewModel.getCharacterDetails(id)
            .test()
            .assertError(error)
    }

    @Test
    fun `display film details WHEN selected`() {
        val film = mock<FilmViewItem>()

        viewModel.onFilmSelected(film)
            .test()

        viewModel.observeDescriptionShow()
            .test()
            .assertValue(film)
    }
}