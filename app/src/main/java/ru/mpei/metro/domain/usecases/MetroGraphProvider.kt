package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.common.MetroGraphParser
import ru.mpei.metro.data.repository.CachingRepository
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.InputStream
import javax.inject.Inject

@ApplicationScope
class MetroGraphProvider @Inject constructor(
    private val cachingRepository: CachingRepository,
    private val metroGraphParser: MetroGraphParser,
) {
    fun getMetroGraph(cityId: String): MetroGraph {
        val decoder = { inputStream: InputStream -> metroGraphParser.fromJson(inputStream) }
        val result = cachingRepository.load(
            key = cityId,
            decoder = decoder
        ).resource
        return result
    }
}
