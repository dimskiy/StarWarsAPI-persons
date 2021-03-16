package `in`.evilcorp.starwarsfinder.platform.network

import `in`.evilcorp.starwarsfinder.BaseRxTest
import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.DomainMapper
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterBaseDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto
import `in`.evilcorp.starwarsfinder.platform.network.pageloader.StarWarsPageLoader
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class NetworkDataProviderTest : BaseRxTest() {

    private val loaderPerson: StarWarsPageLoader<CharacterResultDto> = mock()
    private val loaderSpecies: StarWarsPageLoader<CharacterSpecieDto> = mock()
    private val loaderHomeworld: StarWarsPageLoader<HomeworldDto> = mock()
    private val loaderFilm: StarWarsPageLoader<FilmDto> = mock()
    private val mapper: DomainMapper = mock()

    private lateinit var dataProvider: NetworkDataProvider

    @Before
    fun setup() {
        dataProvider = NetworkDataProvider(
            loaderPerson,
            loaderSpecies,
            loaderHomeworld,
            loaderFilm,
            mapper
        )
    }

    @Test
    fun `characters list load by pages`() {
        val character1 = mock<CharacterBaseDto>()
        val character2 = mock<CharacterBaseDto>()
        val characterResult1 = mock<CharacterResultDto>()
        val characterResult2 = mock<CharacterResultDto>()
        val characterDomain1 = mock<CharacterModel>()
        val characterDomain2 = mock<CharacterModel>()

        whenever(characterResult1.results).thenReturn(listOf(character1))
        whenever(characterResult2.results).thenReturn(listOf(character2))
        whenever(mapper.mapToDomain(characterResult1)).thenReturn(listOf(characterDomain1))
        whenever(mapper.mapToDomain(characterResult2)).thenReturn(listOf(characterDomain2))
        whenever(loaderPerson.observeDataPaged(any())).thenReturn(
            Observable.fromIterable(
                listOf(characterResult1, characterResult2)
            )
        )

        dataProvider.observeCharacters()
            .test()
            .assertValues(
                listOf(characterDomain1),
                listOf(characterDomain1, characterDomain2)
            )
    }

    @Test
    fun `character extra`() {
        val extraDomain = mock<CharacterExtraModel>()

        val specieId = "specieId"
        val specieDto = mock<CharacterSpecieDto>()
        val homeworldUrl = "homeworldUrl"
        val homeworldDto = mock<HomeworldDto>()
        val filmUrl = "filmUrl"
        val filmDto = mock<FilmDto>()

        whenever(specieDto.homeworldUrl).thenReturn(homeworldUrl)
        whenever(specieDto.filmUrls).thenReturn(listOf(filmUrl))
        whenever(loaderSpecies.getData(specieId)).thenReturn(Single.just(specieDto))
        whenever(loaderHomeworld.getData(homeworldUrl)).thenReturn(Single.just(homeworldDto))
        whenever(loaderFilm.getData(filmUrl)).thenReturn(Single.just(filmDto))
        whenever(mapper.mapToDomain(specieDto, homeworldDto, listOf(filmDto))).thenReturn(extraDomain)

        dataProvider.getCharacterExtra(specieId)
            .test()
            .assertValue(extraDomain)
    }

    @Test
    fun `character extra WHEN no homeworld`() {
        val extraDomain = mock<CharacterExtraModel>()

        val specieId = "specieId"
        val specieDto = mock<CharacterSpecieDto>()
        val filmUrl = "filmUrl"
        val filmDto = mock<FilmDto>()

        whenever(specieDto.filmUrls).thenReturn(listOf(filmUrl))
        whenever(loaderSpecies.getData(specieId)).thenReturn(Single.just(specieDto))
        whenever(loaderFilm.getData(filmUrl)).thenReturn(Single.just(filmDto))
        whenever(mapper.mapToDomain(specieDto, HomeworldDto.EMPTY, listOf(filmDto))).thenReturn(extraDomain)

        dataProvider.getCharacterExtra(specieId)
            .test()
            .assertValue(extraDomain)
    }

    @Test
    fun `character extra WHEN no films`() {
        val extraDomain = mock<CharacterExtraModel>()

        val specieId = "specieId"
        val specieDto = mock<CharacterSpecieDto>()
        val homeworldUrl = "homeworldUrl"
        val homeworldDto = mock<HomeworldDto>()

        whenever(specieDto.homeworldUrl).thenReturn(homeworldUrl)
        whenever(loaderSpecies.getData(specieId)).thenReturn(Single.just(specieDto))
        whenever(loaderHomeworld.getData(homeworldUrl)).thenReturn(Single.just(homeworldDto))
        whenever(mapper.mapToDomain(specieDto, homeworldDto, emptyList())).thenReturn(extraDomain)

        dataProvider.getCharacterExtra(specieId)
            .test()
            .assertValue(extraDomain)
    }
}