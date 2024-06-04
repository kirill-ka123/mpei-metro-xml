package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Road(
    val id: String,
    val fromStationId: String,
    val toStationId: String,
    val time: Long,
): Parcelable
