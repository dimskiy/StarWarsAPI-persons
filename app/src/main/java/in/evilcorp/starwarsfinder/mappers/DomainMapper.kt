package `in`.evilcorp.starwarsfinder.mappers

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import `in`.evilcorp.starwarsfinder.domain.models.FilmModel
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.CharacterExtraWithFilms
import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto

interface DomainMapper {

    fun mapToDomain(characterResultDto: CharacterResultDto): List<CharacterModel>

    fun mapToDomain(
        specieDto: CharacterSpecieDto,
        homeworldDto: HomeworldDto,
        filmDtos: List<FilmDto>
    ): CharacterExtraModel

    fun mapToDomain(character: CharacterEntity): CharacterModel

    fun mapToDomain(extra: CharacterExtraWithFilms): CharacterExtraModel

    fun mapToEntity(character: CharacterModel): CharacterEntity

    fun mapToEntity(extra: CharacterExtraModel): CharacterExtraEntity

    fun mapToEntity(film: FilmModel): FilmEntity
}