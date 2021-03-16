package `in`.evilcorp.starwarsfinder.ui.characterlist

import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.ui.characterlist.models.CharacterListViewItem
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class CharacterListViewModel(
    private val interactor: CharactersInteractor,
    private val mapper: UiMapper
) : ViewModel() {

    private val searchQueriesSubject = PublishSubject.create<CharSequence>()
    private val refreshInProgressSubject = PublishSubject.create<Boolean>()
    private val itemsLoadedCountSubject = PublishSubject.create<Int>()
    private val openDetailsSubject = PublishSubject.create<CharacterListViewItem>()

    fun observeCharacters(): Observable<List<CharacterListViewItem>> {
        return interactor.observeCharactersList()
            .flatMap { characters ->
                if (characters.isEmpty()) {
                    onRefreshList().toObservable()
                } else Observable.just(characters)
            }
            .withLoadingCounter()
            .map { characters -> characters.map(mapper::mapToViewListModel) }
    }

    fun observeSearchQueries(): Observable<CharSequence> = searchQueriesSubject
        .debounce(SEARCH_DEBOUNCE_MS, TimeUnit.MILLISECONDS)

    fun observeLoadingVisible(): Observable<Boolean> = refreshInProgressSubject

    fun observeLoadedCount(): Observable<String> = itemsLoadedCountSubject
        .map{ it.toString() }

    fun onSearchQueryChanged(query: CharSequence): Completable = Completable.fromAction {
        searchQueriesSubject.onNext(query)
    }

    fun observeOpenDetails(): Observable<CharacterListViewItem> = openDetailsSubject

    fun onRefreshList(): Completable = interactor.forceUpdateCharacters()
        .withLoadingProgress()

    fun onCharacterSelected(character: CharacterListViewItem): Completable =
        Completable.fromAction {
            openDetailsSubject.onNext(character)
        }

    private fun Completable.withLoadingProgress(): Completable =
        this.doOnSubscribe { refreshInProgressSubject.onNext(true) }
            .doOnComplete { refreshInProgressSubject.onNext(false) }
            .doOnError { refreshInProgressSubject.onNext(false) }

    private fun <T : Collection<*>> Observable<T>.withLoadingCounter(): Observable<T> =
        this.doOnNext { itemsLoadedCountSubject.onNext(it.size) }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
    }
}