package ru.mpei.metro.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val x: Float,
    val y: Float,
) : Parcelable {
    operator fun plus(value: Int): Position {
        return Position(x - value, y - value)
    }

    operator fun plus(value: Float): Position {
        return Position(x - value, y - value)
    }

    operator fun plus(value: Position): Position {
        return Position(x - value.x, y - value.y)
    }

    operator fun minus(value: Int): Position {
        return Position(x - value, y - value)
    }

    operator fun minus(value: Float): Position {
        return Position(x - value, y - value)
    }

    operator fun minus(value: Position): Position {
        return Position(x - value.x, y - value.y)
    }

    operator fun times(value: Int): Position {
        return Position(x * value, y * value)
    }

    operator fun times(value: Float): Position {
        return Position(x * value, y * value)
    }

    operator fun times(value: Position): Position {
        return Position(x * value.x, y * value.y)
    }
}
