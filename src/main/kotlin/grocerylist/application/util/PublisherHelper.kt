package grocerylist.application.util

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.reactivestreams.Publisher

suspend fun <T> Publisher<T>.awaitFirstOrNullWithExceptionHandling(e: () -> Exception): T? {
    return try {
        this.awaitFirstOrNull()
    } catch (e: Exception) {
        throw e()
    }
}