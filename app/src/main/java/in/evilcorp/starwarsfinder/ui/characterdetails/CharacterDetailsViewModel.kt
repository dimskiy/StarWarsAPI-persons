package `in`.evilcorp.starwarsfinder.ui.characterdetails

import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.FilmViewItem
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber

class CharacterDetailsViewModel(
    private val interactor: CharactersInteractor,
    private val mapper: UiMapper
) : ViewModel() {

    private val filmsSubject = BehaviorSubject.create<FilmViewItem>()

    fun getCharacterDetails(characterId: String): Single<CharacterDetailsViewItem> =
        interactor.getBasicCharacterInfo(characterId)
            .doOnSuccess { Timber.d("Got details for speciesId(url): ${it.id}") }
            .flatMap { basicInfo ->
                interactor.getExtraCharacterInfo(characterId)
                    .map { mapper.mapToViewDetails(basicInfo, it) }
                    .toSingle()
                    .onErrorReturnItem(mapper.mapToViewDetails(basicInfo, null))
            }

    fun observeDescriptionShow(): Observable<FilmViewItem> = filmsSubject

    fun onFilmSelected(film: FilmViewItem): Completable = Completable.fromAction {
        filmsSubject.onNext(film)
    }

}