package ru.mpei.metro.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoadEntity(
    val stationId: String,
    val time: Int,
) : Parcelable
