package `in`.evilcorp.starwarsfinder.platform.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FilmEntity.TABLE)
data class FilmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = TITLE) val title: String,
    @ColumnInfo(name = EPISODE_NUM) val episodeNum: Int?,
    @ColumnInfo(name = DESCRIPTION) val description: String?
) {
    companion object {
        const val TABLE = "Film"
        const val ID = "film_id"
        const val TITLE = "title"
        const val EPISODE_NUM = "episode_num"
        const val DESCRIPTION = "description"
    }
}