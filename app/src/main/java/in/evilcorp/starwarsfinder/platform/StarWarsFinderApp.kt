package `in`.evilcorp.starwarsfinder.platform

import `in`.evilcorp.starwarsfinder.BuildConfig
import `in`.evilcorp.starwarsfinder.di.ApplicationComponent
import `in`.evilcorp.starwarsfinder.di.DaggerApplicationComponent
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

fun AppCompatActivity.getAppComponent(): ApplicationComponent =
    application.getAppComponent()

fun Application.getAppComponent(): ApplicationComponent =
    (this as StarWarsFinderApp).appComponent

class StarWarsFinderApp: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        appComponent = DaggerApplicationComponent.factory().create(this)
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}