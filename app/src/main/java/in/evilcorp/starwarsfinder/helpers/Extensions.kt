package `in`.evilcorp.starwarsfinder.helpers

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.untilClear(compositeDisposable: CompositeDisposable): Disposable {
    compositeDisposable.add(this)
    return this
}