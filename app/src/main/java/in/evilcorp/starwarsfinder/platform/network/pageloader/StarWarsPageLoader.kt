package `in`.evilcorp.starwarsfinder.platform.network.pageloader

import `in`.evilcorp.starwarsfinder.platform.network.dto.StarWarsFetchableDto
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.InterruptedIOException
import okhttp3.OkHttpClient
import okhttp3.Request

class StarWarsPageLoader<T : StarWarsFetchableDto>(
    private val client: OkHttpClient,
    private val resultClass: Class<T>
) {

    fun observeDataPaged(url: String): Observable<T> =
        Observable.create { emitter ->
            var nextUrl: String? = url
            while (nextUrl?.isNotEmpty() == true) {
                try {
                    val result = loadResult(nextUrl)
                    nextUrl = result?.next
                    emitter.onNext(result)
                } catch (e: InterruptedIOException) {}
                catch (e: Throwable) {
                    emitter.onError(e)
                }
            }
            emitter.onComplete()
        }

    fun getData(url: String): Single<T> = Single.create { emitter ->
        try {
            val result = loadResult(url)
            emitter.onSuccess(result)
        } catch (e: InterruptedIOException) {
        } catch (e: Throwable) {
            emitter.onError(e)
        }
    }

    private fun loadResult(url: String): T? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute().body

        return Gson().fromJson(
            response?.string(),
            resultClass
        )
    }
}