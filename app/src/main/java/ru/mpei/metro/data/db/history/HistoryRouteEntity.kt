package ru.mpei.metro.data.db.history

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import ru.mpei.metro.data.db.converters.StationConverter
import ru.mpei.metro.domain.model.Station

@Entity(
    tableName = "history_routes",
    primaryKeys = ["fromStation", "toStation"]
)
@TypeConverters(StationConverter::class)
@Parcelize
data class HistoryRouteEntity(
    val id: Int = 0,
    val fromStation: Station,
    val toStation: Station,
    val timestamp: String,
) : Parcelable
