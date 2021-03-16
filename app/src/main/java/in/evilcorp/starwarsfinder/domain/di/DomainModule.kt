package `in`.evilcorp.starwarsfinder.domain.di

import `in`.evilcorp.starwarsfinder.domain.CharactersInteractor
import `in`.evilcorp.starwarsfinder.domain.CharactersRepository
import `in`.evilcorp.starwarsfinder.domain.impl.CharactersInteractorImpl
import `in`.evilcorp.starwarsfinder.domain.impl.CharactersRepositoryImpl
import `in`.evilcorp.starwarsfinder.mappers.DomainMapper
import `in`.evilcorp.starwarsfinder.mappers.DomainMapperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindCharactersInteractor(interactor: CharactersInteractorImpl): CharactersInteractor

    @Binds
    @Singleton
    abstract fun bindCharactersRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    @Singleton
    abstract fun bindDomainMapper(mapper: DomainMapperImpl): DomainMapper

}