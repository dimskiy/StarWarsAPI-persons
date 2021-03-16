package `in`.evilcorp.starwarsfinder.platform.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharacterEntity.TABLE)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = BIRTH_YEAR) val birthYear: String?,
    @ColumnInfo(name = HEIGHT) val height: Double?,
    @ColumnInfo(name = EXTRAS_ID) val extrasId: String?
) {
    companion object {
        const val TABLE = "Character"
        const val ID = "character_id"
        const val NAME = "name"
        const val BIRTH_YEAR = "birth_year"
        const val HEIGHT = "height"
        const val EXTRAS_ID = "extras_server_id"
    }
}