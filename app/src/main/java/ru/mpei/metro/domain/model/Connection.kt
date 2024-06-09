package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Connection(
    val id: String,
    val fromStationId: String,
    val toStationId: String,
    val time: Int,
): Parcelable
