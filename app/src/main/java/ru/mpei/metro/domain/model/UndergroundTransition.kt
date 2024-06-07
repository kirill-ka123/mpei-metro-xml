package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UndergroundTransition(
    val id: String,
    val stationIds: List<String>,
): Parcelable
