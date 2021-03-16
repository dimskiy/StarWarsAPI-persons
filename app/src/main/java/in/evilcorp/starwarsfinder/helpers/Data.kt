package `in`.evilcorp.starwarsfinder.helpers

data class Data<T>(
    val content: T? = null,
    val loading: Boolean = true,
    val error: Throwable? = null
)

fun <T> Data<T>.isStateError(): Boolean = error != null

fun <T> Data<T>.isStateLoading(): Boolean = loading && content == null && error == null

fun <T> Data<T>.isContentNotEmpty(): Boolean = content != null