package `in`.evilcorp.starwarsfinder.platform.database

import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.CharacterExtraWithFilms
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.ExtraCrossFilmsRef
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class StarWarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun putCharacters(characters: List<CharacterEntity>): Completable

    @Query("""
        SELECT *
        FROM ${CharacterEntity.TABLE}
        ORDER BY ${CharacterEntity.NAME} ASC 
    """)
    abstract fun selectAllCharacters(): Observable<List<CharacterEntity>>

    @Query("""
        SELECT *
        FROM ${CharacterEntity.TABLE}
        WHERE ${CharacterEntity.ID} = :characterId
        LIMIT 1
    """)
    abstract fun selectCharacter(characterId: String): Single<CharacterEntity>

    @Transaction
    @Query("""
        SELECT *
        FROM ${CharacterExtraEntity.TABLE}
        WHERE ${CharacterExtraEntity.ID} = :specieId
    """)
    abstract fun selectCharacterExtra(specieId: String): Maybe<CharacterExtraWithFilms>

    @Transaction
    open fun updateExtraWithFilmsTransaction(extra: CharacterExtraEntity, films: List<FilmEntity>) {
        updateExtra(extra)
        updateFilms(films)

        val links = films.map {
            ExtraCrossFilmsRef(
                characterExtraId = extra.id,
                filmId = it.id
            )
        }
        updateExtraFilmsLinks(links)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateExtra(extra: CharacterExtraEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateFilms(films: List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updateExtraFilmsLinks(extrasWithFilms: List<ExtraCrossFilmsRef>)
}