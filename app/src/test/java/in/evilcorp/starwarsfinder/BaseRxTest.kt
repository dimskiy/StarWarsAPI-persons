package `in`.evilcorp.starwarsfinder

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before

open class BaseRxTest {

    @Before
    fun prepare() {
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun calmDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}