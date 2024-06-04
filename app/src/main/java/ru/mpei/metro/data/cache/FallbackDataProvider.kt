package ru.mpei.metro.data.cache

import java.io.InputStream

fun interface FallbackDataProvider {
    fun getFallbackData(): InputStream
}
