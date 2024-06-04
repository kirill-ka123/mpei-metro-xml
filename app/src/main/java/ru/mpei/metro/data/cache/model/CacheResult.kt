package ru.mpei.metro.data.cache.model

data class CacheResult<out Resource>(
    val resource: Resource,
    val freshUntilTimestamp: Long
)
