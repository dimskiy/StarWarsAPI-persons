package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.domain.models.FilmModel
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.CharacterExtraWithFilms
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterBaseDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto
import javax.inject.Inject

class DomainMapperImpl @Inject constructor() : DomainMapper {

    override fun mapToDomain(characterResultDto: CharacterResultDto): List<CharacterModel> =
        characterResultDto.results.map(this::mapToDomain)

    private fun mapToDomain(characterDto: CharacterBaseDto): CharacterModel = CharacterModel(
        id = characterDto.url,
        name = characterDto.name,
        birthYear = characterDto.birthYear,
        height = characterDto.height?.toDoubleOrNull(),
        speciesId = characterDto.speciesUrls?.firstOrNull()
    )

    override fun mapToDomain(
        specieDto: CharacterSpecieDto,
        homeworldDto: HomeworldDto,
        filmDtos: List<FilmDto>
    ): CharacterExtraModel =
        CharacterExtraModel(
            id = specieDto.url,
            raceName = specieDto.raceName,
            language = specieDto.language,
            homeWorld = homeworldDto.name,
            population = homeworldDto.population?.toLongOrNull(),
            films = filmDtos.map(this::mapToDomain)
        )

    private fun mapToDomain(filmDto: FilmDto): FilmModel = FilmModel(
        id = filmDto.url,
        title = filmDto.title,
        episodeNumber = filmDto.episodeNumber,
        description = filmDto.description
    )

    override fun mapToDomain(character: CharacterEntity): CharacterModel = CharacterModel(
        id = character.id,
        name = character.name,
        birthYear = character.birthYear,
        height = character.height,
        speciesId = character.extrasId
    )

    override fun mapToDomain(extra: CharacterExtraWithFilms): CharacterExtraModel =
        with(extra) {
            CharacterExtraModel(
                id = this.extra.id,
                raceName = this.extra.raceName,
                language = this.extra.language,
                homeWorld = this.extra.homeWorld,
                population = this.extra.population,
                films = films.map(this@DomainMapperImpl::mapToDomain)
            )
        }

    private fun mapToDomain(entity: FilmEntity) = FilmModel(
        id = entity.id,
        title = entity.title,
        episodeNumber = entity.episodeNum,
        description = entity.description
    )

    override fun mapToEntity(character: CharacterModel): CharacterEntity =
        CharacterEntity(
            id = character.id,
            name = character.name,
            birthYear = character.birthYear,
            height = character.height,
            extrasId = character.speciesId
        )

    override fun mapToEntity(extra: CharacterExtraModel) = CharacterExtraEntity(
        id = extra.id,
        raceName = extra.raceName,
        language = extra.language,
        population = extra.population,
        homeWorld = extra.homeWorld
    )

    override fun mapToEntity(film: FilmModel) = FilmEntity(
        id = film.id,
        episodeNum = film.episodeNumber,
        title = film.title,
        description = film.description
    )
}