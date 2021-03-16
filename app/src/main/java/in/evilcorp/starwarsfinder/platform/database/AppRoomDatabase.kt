package `in`.evilcorp.starwarsfinder.platform.database

import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.CharacterExtraEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.FilmEntity
import `in`.evilcorp.starwarsfinder.platform.database.entity.relation.ExtraCrossFilmsRef
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class,
        CharacterExtraEntity::class,
        FilmEntity::class,
        ExtraCrossFilmsRef::class
    ], version = 1, exportSchema = true
)
abstract class AppRoomDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "AppDatabase.db"
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(appContext: Context) = INSTANCE
            ?: Room.databaseBuilder(appContext, AppRoomDatabase::class.java, DB_NAME).build()
    }

    abstract fun getStarWarsDao(): StarWarsDao
}