package `in`.evilcorp.starwarsfinder.platform.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharacterExtraEntity.TABLE)
data class CharacterExtraEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = RACE_NAME) val raceName: String?,
    @ColumnInfo(name = LANGUAGE) val language: String?,
    @ColumnInfo(name = HOME_WORLD) val homeWorld: String?,
    @ColumnInfo(name = POPULATION) val population: Long?
) {
    companion object {
        const val TABLE = "CharacterExtra"
        const val ID = "extra_id"
        const val RACE_NAME = "race_name"
        const val LANGUAGE = "language"
        const val HOME_WORLD = "home_world"
        const val POPULATION = "population"
    }
}