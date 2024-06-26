package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transition(
    val id: String,
    val stationIds: List<String>,
    val overgroundStationIds: List<String>,
): Parcelable
