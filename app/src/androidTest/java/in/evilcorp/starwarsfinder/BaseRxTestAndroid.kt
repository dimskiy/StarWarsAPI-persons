package `in`.evilcorp.starwarsfinder

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseRxTestAndroid {

    @Before
    fun prepare() {
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun after() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}