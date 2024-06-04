package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    val id: String,
    val name: String,
    val position: Position,
    val lat: Float = 0f,
    val lon: Float = 0f,
    val branch: Branch,
    val transition: Transition? = null,
    val transitionsRoads: List<Road>,
): Parcelable
