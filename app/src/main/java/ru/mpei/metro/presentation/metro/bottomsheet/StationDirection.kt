package ru.mpei.metro.presentation.metro.bottomsheet

import androidx.annotation.StringRes
import ru.mpei.metro.R

enum class StationDirection(
    @StringRes val title: Int
) {
    TO(R.string.to),
    FROM(R.string.from),
}
