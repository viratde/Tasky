package com.tasky.core.domain.util

sealed interface Result<out D, out E> {

    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.tasky.core.domain.util.Error>(val error: E) : Result<Nothing, E>

}


fun <T, E : Error, R> Result<T, E>.mapData(
    mapData: (data: T) -> R
): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(mapData(data))
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return mapData { }
}

typealias EmptyDataResult<E> = Result<Unit, E>