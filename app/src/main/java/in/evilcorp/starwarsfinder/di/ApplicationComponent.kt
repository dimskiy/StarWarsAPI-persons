package `in`.evilcorp.starwarsfinder.di

import `in`.evilcorp.starwarsfinder.domain.di.DomainModule
import `in`.evilcorp.starwarsfinder.platform.di.LoadersModule
import `in`.evilcorp.starwarsfinder.platform.di.ServiceModule
import `in`.evilcorp.starwarsfinder.ui.characterdetails.CharacterDetailsActivity
import `in`.evilcorp.starwarsfinder.ui.characterlist.CharacterListActivity
import `in`.evilcorp.starwarsfinder.ui.di.UiModule
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UiModule::class,
        DomainModule::class,
        ServiceModule::class,
        LoadersModule::class
    ]
)
interface ApplicationComponent {

    fun inject(listActivity: CharacterListActivity)
    fun inject(detailsActivity: CharacterDetailsActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}