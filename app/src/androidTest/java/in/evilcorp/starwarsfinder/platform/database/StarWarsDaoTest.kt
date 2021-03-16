package `in`.evilcorp.starwarsfinder.platform.database

import `in`.evilcorp.starwarsfinder.BaseRxTestAndroid
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import org.junit.After
import org.junit.Before

class StarWarsDaoTest : BaseRxTestAndroid() {

    private lateinit var db: AppRoomDatabase
    private lateinit var dao: StarWarsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppRoomDatabase::class.java).build()

        dao = db.getStarWarsDao()
    }

    @After
    @Throws(IOException::class)
    fun calmDown() {
        db.close()
    }
}