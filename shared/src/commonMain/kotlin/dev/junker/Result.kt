package dev.junker

import kotlin.jvm.JvmInline

sealed interface Result<out T, out E> {
    @JvmInline
    value class Ok<T>(
        val value: T
    ) : Result<T, Nothing>

    @JvmInline
    value class Error<E>(
        val error: E
    ) : Result<Nothing, E>
}

fun <T> T.ok(): Result.Ok<T> {
    return Result.Ok(this)
}

fun <E> E.err(): Result.Error<E> {
    return Result.Error(this)
}

inline fun <T, E, U> Result<T, E>.ifOk(
    crossinline transform: (T) -> U
): Result<U, E> {
    return when (this) {
        is Result.Ok -> transform(value).ok()
        is Result.Error -> this
    }
}

inline fun <T, E, U> Result<T, E>.ifOkTry(
    crossinline transform: (T) -> Result<U, E>
): Result<U, E> {
    return when (this) {
        is Result.Ok -> transform(value)
        is Result.Error -> this
    }
}

inline fun <T, E, F> Result<T, E>.ifError(
    crossinline transform: (E) -> F
): Result<T, F> {
    return when (this) {
        is Result.Ok -> this
        is Result.Error -> transform(error).err()
    }
}

fun <T, E> T?.orElse(error: E): Result<T, E> {
    return when (this) {
        null -> error.err()
        else -> ok()
    }
}
