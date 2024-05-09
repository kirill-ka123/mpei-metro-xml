package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mpei.metro.data.model.RoadEntity

@Parcelize
data class Station(
    val id: String,
    val name: String,
    val position: Position,
    val lat: Float = 0f,
    val lon: Float = 0f,
    val branch: Branch,
    val transaction: Transition?,
    val roadConnections: List<RoadEntity>,
) : Parcelable
