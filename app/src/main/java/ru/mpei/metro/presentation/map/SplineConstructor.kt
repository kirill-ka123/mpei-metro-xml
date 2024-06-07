package ru.mpei.metro.presentation.map

import android.graphics.Path
import ru.mpei.metro.domain.model.Position


object SplineConstructor {
    fun constructSpline(path: Path, positions: List<Position>) {
        val initialPosition = positions.first()
        path.moveTo(initialPosition.x, initialPosition.y)

        for (index in 1 until positions.size) {
            val current = positions[index]
            val previous = positions.getOrElse(index - 1) { current }
            val next = positions.getOrElse(index + 1) { current }
            val nextNext = positions.getOrElse(index + 2) { next }

            val firstControlPoint = calculateControlPoint(previous, current, next)
            val secondControlPoint = calculateControlInvertedPoint(current, next, nextNext)

            path.cubicTo(
                firstControlPoint.x,
                firstControlPoint.y,
                secondControlPoint.x,
                secondControlPoint.y,
                next.x,
                next.y,
            )
        }
    }

    private fun calculateControlPoint(
        prev: Position,
        curr: Position,
        next: Position,
    ): Position {
        val dx = (next.x - prev.x) / 6
        val dy = (next.y - prev.y) / 6
        return Position(curr.x + dx, curr.y + dy)
    }


    private fun calculateControlInvertedPoint(
        curr: Position,
        next: Position,
        nextNext: Position,
    ): Position {
        val dx = (nextNext.x - curr.x) / 6
        val dy = (nextNext.y - curr.y) / 6
        return Position(next.x - dx, next.y - dy)
    }
}
