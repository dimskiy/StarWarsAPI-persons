package `in`.evilcorp.starwarsfinder.ui.di

import `in`.evilcorp.starwarsfinder.mappers.UiMapper
import `in`.evilcorp.starwarsfinder.mappers.UiMapperImpl
import `in`.evilcorp.starwarsfinder.ui.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UiModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun bindUiMapper(mapper: UiMapperImpl): UiMapper

}