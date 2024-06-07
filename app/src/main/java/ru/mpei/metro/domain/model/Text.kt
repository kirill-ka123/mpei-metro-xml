package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Text(
    val text: String,
    val position: Position,
    val anchor: String,
): Parcelable
