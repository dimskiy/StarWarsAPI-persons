package `in`.evilcorp.starwarsfinder.helpers

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class RxDisposableDelegate: RxDisposable {
    private val compositeDisposable = CompositeDisposable()

    override fun Disposable.untilClear() {
        compositeDisposable.add(this)
    }

    override fun clearAll() {
        compositeDisposable.clear()
    }
}