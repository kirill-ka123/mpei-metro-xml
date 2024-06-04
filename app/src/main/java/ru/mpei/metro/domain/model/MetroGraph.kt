package ru.mpei.metro.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import ru.mpei.metro.data.db.converters.StationConverter

@Parcelize
data class MetroGraph(
    val id: String,
    val cityName: String,
    val stations: List<Station>,
    val branches: List<Branch>,
    val transitions: List<Transition>,
    val schedule: List<Road>,
): Parcelable
