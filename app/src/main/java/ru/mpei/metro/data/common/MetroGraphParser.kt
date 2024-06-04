package ru.mpei.metro.data.common

import com.google.gson.GsonBuilder
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@ApplicationScope
class MetroGraphParser @Inject constructor() {
    private val gson = GsonBuilder().create()

    fun fromJson(data: InputStream): MetroGraph {
        val reader = InputStreamReader(data)
        return gson.fromJson(reader, MetroGraph::class.java)
    }

    fun toJson(metroGraph: MetroGraph): String {
        return gson.toJson(metroGraph)
    }
}
