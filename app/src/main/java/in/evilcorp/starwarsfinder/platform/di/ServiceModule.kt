package `in`.evilcorp.starwarsfinder.platform.di

import `in`.evilcorp.starwarsfinder.domain.DataProvider
import `in`.evilcorp.starwarsfinder.domain.DataStorage
import `in`.evilcorp.starwarsfinder.platform.database.AppRoomDatabase
import `in`.evilcorp.starwarsfinder.platform.database.LocalDataStorage
import `in`.evilcorp.starwarsfinder.platform.database.StarWarsDao
import `in`.evilcorp.starwarsfinder.platform.network.NetworkDataProvider
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@Module
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkDataProvider(provider: NetworkDataProvider): DataProvider

    @Binds
    @Singleton
    abstract fun bindLocalDataStorage(storage: LocalDataStorage): DataStorage

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor(Timber::d)
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideStarWarsDao(context: Context): StarWarsDao =
            AppRoomDatabase.getInstance(context).getStarWarsDao()
    }
}