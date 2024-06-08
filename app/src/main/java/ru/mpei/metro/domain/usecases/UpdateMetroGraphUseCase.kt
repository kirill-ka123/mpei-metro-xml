package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.cache.model.DecodeResult
import ru.mpei.metro.domain.graph.MetroGraphParser
import ru.mpei.metro.data.repository.CachingRepository
import ru.mpei.metro.data.repository.MetroRepository
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.InputStream
import javax.inject.Inject

@ApplicationScope
class UpdateMetroGraphUseCase @Inject constructor(
    private val metroRepository: MetroRepository,
    private val cachingRepository: CachingRepository,
    private val metroGraphParser: MetroGraphParser,
) {
    suspend fun updateMetroGraph(cityId: String) {
        val metroGraphResponse = metroRepository.getMetroGraphFromNetwork()
        if (metroGraphResponse.isSuccessful) {
            val metroGraph = metroGraphResponse.body() ?: return
            cachingRepository.save(
                key = cityId,
                data = metroGraphParser.toJson(metroGraph).byteInputStream(),
                decoder = { inputStream: InputStream ->
                    try {
                        DecodeResult.Success(metroGraphParser.fromJson(inputStream))
                    } catch (error: Throwable) {
                        DecodeResult.Fail(error.toString())
                    }
                }
            )
        }
    }
}
