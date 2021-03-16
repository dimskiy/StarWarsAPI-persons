package `in`.evilcorp.starwarsfinder.helpers

import io.reactivex.rxjava3.disposables.Disposable

interface RxDisposable {

    fun Disposable.untilClear()

    fun clearAll()
}