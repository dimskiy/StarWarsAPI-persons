package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.mappers.UiMapperImpl.Companion.INCH_CM_RATIO
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UiMapperImplTest {

    private lateinit var mapper: UiMapperImpl

    @Before
    fun setup() {
        mapper = UiMapperImpl()
    }

    @Test
    fun `map mapToViewDetails WHEN no films`() {
        val character = CharacterModel(
            id = "id",
            name = "name",
            birthYear = "19BBY",
            height = 187.0,
            speciesId = "speciesId"
        )
        val characterExtra = CharacterExtraModel(
            id = "characterExtraUrl",
            raceName = "raceName",
            language = "language",
            homeWorld = "homeWorld",
            population = 1000,
            films = emptyList()
        )

        val expected = CharacterDetailsViewItem(
            id = character.id,
            name = character.name,
            birthYear = character.birthYear,
            heightCm = character.height,
            heightInch = character.height!! / INCH_CM_RATIO,
            raceName = characterExtra.raceName,
            language = characterExtra.language,
            homeworld = characterExtra.homeWorld,
            population = characterExtra.population,
            films = emptyList()
        )

        assertEquals(
            expected,
            mapper.mapToViewDetails(character, characterExtra)
        )
    }
}