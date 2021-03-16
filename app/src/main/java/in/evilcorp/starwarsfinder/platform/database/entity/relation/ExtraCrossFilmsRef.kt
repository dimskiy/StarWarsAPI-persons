package `in`.evilcorp.starwarsfinder.platform.database.entity.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = [ExtraCrossFilmsRef.EXTRA_ID, ExtraCrossFilmsRef.FILM_ID])
class ExtraCrossFilmsRef(
    @ColumnInfo(name = EXTRA_ID, index = true) val characterExtraId: String,
    @ColumnInfo(name = FILM_ID, index = true) val filmId: String
) {
    companion object {
        const val EXTRA_ID = "extra_id"
        const val FILM_ID = "film_id"
    }
}