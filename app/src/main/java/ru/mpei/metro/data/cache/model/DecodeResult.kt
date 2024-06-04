package ru.mpei.metro.data.cache.model

sealed interface DecodeResult<R, F> {

    class Success<R, F>(val result: R) : DecodeResult<R, F>

    class Fail<R, F>(val failure: F) : DecodeResult<R, F>
}