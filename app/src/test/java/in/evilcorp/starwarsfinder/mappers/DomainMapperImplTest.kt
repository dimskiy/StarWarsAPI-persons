package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.domain.models.FilmModel
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterBaseDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DomainMapperImplTest {
    private lateinit var mapper: DomainMapperImpl

    @Before
    fun setup() {
        mapper = DomainMapperImpl()
    }

    @Test
    fun `map character result dto WHEN wrong height`() {
        val characterDto = CharacterBaseDto(
            url = "url",
            name = "name",
            height = "A187",
            birthYear = null,
            speciesUrls = listOf("specieUrl")
        )

        val resultDto = CharacterResultDto(
            count = 1,
            next = null,
            previous = null,
            results = listOf(characterDto)
        )

        val expectedModel = CharacterModel(
            id = characterDto.url,
            name = characterDto.name,
            birthYear = null,
            height = null,
            speciesId = "specieUrl"
        )

        assertEquals(
            listOf(expectedModel),
            mapper.mapToDomain(resultDto)
        )
    }

    @Test
    fun `map character result dto WHEN no spicie`() {
        val characterDto = CharacterBaseDto(
            url = "url",
            name = "name",
            height = "A187",
            birthYear = null,
            speciesUrls = emptyList()
        )

        val resultDto = CharacterResultDto(
            count = 1,
            next = null,
            previous = null,
            results = listOf(characterDto)
        )

        val expectedModel = CharacterModel(
            id = characterDto.url,
            name = characterDto.name,
            birthYear = null,
            height = null,
            speciesId = null
        )

        assertEquals(
            listOf(expectedModel),
            mapper.mapToDomain(resultDto)
        )
    }

    @Test
    fun `map CharacterExtraModel WHEN population wrong`() {
        val filmUrl = "filmUrl"
        val homeworldUrl = "homeworldUrl"

        val specieDto = CharacterSpecieDto(
            url = "specieUrl",
            raceName = "raceName",
            language = "language",
            homeworldUrl = homeworldUrl,
            filmUrls = listOf(filmUrl)
        )
        val homeworldDto = HomeworldDto(
            url = homeworldUrl,
            name = "name",
            population = "A1000"
        )
        val filmDto = FilmDto(
            url = filmUrl,
            title = "title",
            episodeNumber = 1,
            description = "description"
        )

        val expected = CharacterExtraModel(
            id = specieDto.url,
            raceName = specieDto.raceName,
            language = specieDto.language,
            homeWorld = homeworldDto.name,
            population = null,
            films = listOf(
                FilmModel(
                    id = filmDto.url,
                    title = filmDto.title,
                    episodeNumber = filmDto.episodeNumber,
                    description = filmDto.description
                )
            )
        )

        assertEquals(
            expected,
            mapper.mapToDomain(specieDto, homeworldDto, listOf(filmDto))
        )
    }
}