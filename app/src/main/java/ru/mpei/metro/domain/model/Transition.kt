package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transition(
    val id: String,
    val time: Int,
    val stationsIds: List<String>,
): Parcelable
