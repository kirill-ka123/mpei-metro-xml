package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Branch(
    val id: String,
    val name: String,
    val hexColor: String,
    val stationsIds: List<String>,
): Parcelable
