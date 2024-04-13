package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Road(
    val station: Station,
    val time: Int,
) : Parcelable
