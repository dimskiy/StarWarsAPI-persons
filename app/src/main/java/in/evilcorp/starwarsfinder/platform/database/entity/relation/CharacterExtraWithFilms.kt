package `in`.evilcorp.starwarsfinder.platform.database.entity.relation

import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CharacterExtraWithFilms(
    @Embedded
    val extra: CharacterExtraEntity,

    @Relation(
        parentColumn = CharacterExtraEntity.ID,
        entityColumn = FilmEntity.ID,
        associateBy = Junction(ExtraCrossFilmsRef::class)
    )
    val films: List<FilmEntity>
)