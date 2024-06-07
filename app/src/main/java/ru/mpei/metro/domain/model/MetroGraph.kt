package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetroGraph(
    val id: String,
    val cityName: String,
    val stations: List<Station>,
    val branches: List<Branch>,
    val undergroundTransitions: List<UndergroundTransition>,
    val groundTransitions: List<GroundTransition>,
    val schedule: List<Road>,
    val texts: List<Text>,
): Parcelable
