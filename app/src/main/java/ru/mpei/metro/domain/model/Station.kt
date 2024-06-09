package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    val id: String,
    val stationName: String,
    val branchId: String,
    val branchName: String,
    val position: Position,
    val hexColor: String,
    val lat: Float = 0f,
    val lon: Float = 0f,
): Parcelable
