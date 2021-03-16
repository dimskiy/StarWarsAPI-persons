package `in`.evilcorp.starwarsfinder.platform.network

import `in`.evilcorp.starwarsfinder.domain.DataProvider
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.DomainMapper
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto
import `in`.evilcorp.starwarsfinder.platform.network.pageloader.StarWarsPageLoader
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NetworkDataProvider @Inject constructor(
    private val loaderPerson: StarWarsPageLoader<CharacterResultDto>,
    private val loaderSpecies: StarWarsPageLoader<CharacterSpecieDto>,
    private val loaderHomeworld: StarWarsPageLoader<HomeworldDto>,
    private val loaderFilm: StarWarsPageLoader<FilmDto>,
    private val mapper: DomainMapper
) : DataProvider {

    override fun observeCharacters(): Observable<List<CharacterModel>> =
        loaderPerson.observeDataPaged(PEOPLE_BASE_URL)
            .map(mapper::mapToDomain)
            .scan { accumulator, page -> (accumulator + page) }

    override fun getCharacterExtra(specieId: String): Maybe<CharacterExtraModel> =
        loaderSpecies.getData(specieId)
            .flatMapMaybe { specie ->
                Single.zip(
                    getHomeworld(specie.homeworldUrl),
                    getFilms(specie.filmUrls)
                ) { homeworld, films -> mapper.mapToDomain(specie, homeworld, films) }
                    .toMaybe()
            }

    private fun getHomeworld(url: String?): Single<HomeworldDto> =
        url?.takeIf(String::isNotEmpty)?.let(loaderHomeworld::getData)
            ?: Single.just(HomeworldDto.EMPTY)

    private fun getFilms(urls: List<String>?): Single<List<FilmDto>> =
        Observable.fromIterable(urls.orEmpty())
            .flatMapSingle(loaderFilm::getData)
            .toList()

    companion object {
        private const val PEOPLE_BASE_URL = "https://swapi.dev/api/people/"

        private const val LOADER_PERSON_KEY = "LOADER_PERSON"
        private const val LOADER_SPECIES_KEY = "LOADER_SPECIES"
        private const val LOADER_HOMEWORLD_KEY = "LOADER_HOMEWORLD"
        private const val LOADER_FILM_KEY = "LOADER_FILM"
    }
}