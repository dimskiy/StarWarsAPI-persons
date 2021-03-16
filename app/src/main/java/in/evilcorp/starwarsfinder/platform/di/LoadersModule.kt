package `in`.evilcorp.starwarsfinder.platform.di

import `in`.evilcorp.starwarsfinder.platform.network.dto.CharacterResultDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.CharacterSpecieDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.FilmDto
import `in`.evilcorp.starwarsfinder.platform.network.dto.person.species.HomeworldDto
import `in`.evilcorp.starwarsfinder.platform.network.pageloader.StarWarsPageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
open class LoadersModule {

    @Provides
    @Singleton
    fun providePersonsLoader(client: OkHttpClient): StarWarsPageLoader<CharacterResultDto> =
        StarWarsPageLoader(
            client = client,
            resultClass = CharacterResultDto::class.java
        )

    @Provides
    @Singleton
    fun provideSpeciesLoader(client: OkHttpClient): StarWarsPageLoader<CharacterSpecieDto> =
        StarWarsPageLoader(
            client = client,
            resultClass = CharacterSpecieDto::class.java
        )

    @Provides
    @Singleton
    fun provideHomeworldLoader(client: OkHttpClient): StarWarsPageLoader<HomeworldDto> =
        StarWarsPageLoader(
            client = client,
            resultClass = HomeworldDto::class.java
        )

    @Provides
    @Singleton
    fun provideFilmLoader(client: OkHttpClient): StarWarsPageLoader<FilmDto> =
        StarWarsPageLoader(
            client = client,
            resultClass = FilmDto::class.java
        )
}