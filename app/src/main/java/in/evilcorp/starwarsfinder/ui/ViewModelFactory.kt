package `in`.evilcorp.starwarsfinder.ui

import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.ui.characterdetails.CharacterDetailsViewModel
import `in`.evilcorp.starwarsfinder.ui.characterlist.CharacterListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val charactersInteractor: CharactersInteractor,
    private val uiMapper: UiMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterListViewModel::class.java) -> {
                CharacterListViewModel(charactersInteractor, uiMapper) as T
            }
            modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java) -> {
                CharacterDetailsViewModel(charactersInteractor, uiMapper) as T
            }
            else -> throw IllegalStateException("Requested model class not found: ${modelClass.simpleName}")
        }
    }
}