package com.example.bizlink.data.remote.retrofit.exeption

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchWithHandler(
    exceptionHandlerDelegate: ExaptionHandlerDelegate,
    onError: FlowCollector<T>.(Throwable) -> Unit,
): Flow<T> = catch { ex ->
    exceptionHandlerDelegate.handleException(ex)
    onError(ex)
}

inline fun <T, R> T.runCatching(
    exceptionHandlerDelegate: ExaptionHandlerDelegate,
    block: T.() -> R,
): Result<R> {
    return try {
        Result.success(block())
    } catch (ex: Throwable) {
        Result.failure(exceptionHandlerDelegate.handleException(ex))
    }
}