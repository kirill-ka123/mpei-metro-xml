package ru.mpei.metro.presentation.map

import android.graphics.Path
import ru.mpei.metro.domain.model.Position
import kotlin.math.sqrt


object SplineConstructor {
    fun constructSpline(path: Path, positions: List<Position>) {
        if (positions.size < 2) {
            throw IllegalArgumentException("At least two positions are required to construct a spline.")
        }

        val initialPosition = positions.first()
        path.moveTo(initialPosition.x, initialPosition.y)

        if (positions.size == 2) {
            val finalPosition = positions.last()
            path.lineTo(finalPosition.x, finalPosition.y)
            return
        }

        val controlPoints = calculateControlPoints(positions)

        for (i in 0 until positions.size - 1) {
            val p1 = positions[i + 1]
            val c0 = controlPoints[i * 2]
            val c1 = controlPoints[i * 2 + 1]

            path.cubicTo(c0.x, c0.y, c1.x, c1.y, p1.x, p1.y)
        }
    }

    private fun calculateControlPoints(positions: List<Position>): List<Position> {
        val controlPoints = mutableListOf<Position>()

        for (i in 0 until positions.size - 1) {
            val p0 = positions[i]
            val p1 = positions[i + 1]
            val p2 = if (i < positions.size - 2) positions[i + 2] else p1

            val d1 = distance(p0, p1)
            val d2 = distance(p1, p2)

            val c1 = if (i == 0) {
                Position(
                    p0.x + (p1.x - p0.x) / 3,
                    p0.y + (p1.y - p0.y) / 3
                )
            } else {
                Position(
                    p0.x + (p1.x - positions[i - 1].x) / (6 * d1) * d1,
                    p0.y + (p1.y - positions[i - 1].y) / (6 * d1) * d1
                )
            }

            val c2 = if (i == positions.size - 2) {
                Position(
                    p1.x - (p2.x - p1.x) / 3,
                    p1.y - (p2.y - p1.y) / 3
                )
            } else {
                Position(
                    p1.x - (positions[i + 2].x - p0.x) / (6 * d2) * d1,
                    p1.y - (positions[i + 2].y - p0.y) / (6 * d2) * d1
                )
            }

            controlPoints.add(c1)
            controlPoints.add(c2)
        }

        return controlPoints
    }


    private fun distance(p1: Position, p2: Position): Float {
        val dx = p2.x - p1.x
        val dy = p2.y - p1.y
        return sqrt(dx * dx + dy * dy)
    }
}
