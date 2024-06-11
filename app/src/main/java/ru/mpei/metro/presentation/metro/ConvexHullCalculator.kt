package ru.mpei.metro.presentation.metro

import ru.mpei.metro.domain.model.Position

object ConvexHullCalculator {
    fun calculateConvexHull(points: List<Position>): List<Position> {
        val sortedPoints = points.sortedWith(compareBy({ it.x }, { it.y })).toMutableList()
        val lowerHull = mutableListOf<Position>()
        val upperHull = mutableListOf<Position>()

        for (hull in arrayOf(lowerHull, upperHull)) {
            for (point in sortedPoints) {
                while (hull.size >= 2 && crossProductZ(hull[hull.size - 2], hull[hull.size - 1], point) <= 0) {
                    hull.removeAt(hull.size - 1)
                }
                hull.add(point)
            }
            sortedPoints.reverse()
        }

        // Remove last point of each half because it is repeated at the beginning of the other half
        lowerHull.removeAt(lowerHull.size - 1)
        upperHull.removeAt(upperHull.size - 1)

        return lowerHull + upperHull
    }

    private fun crossProductZ(o: Position, a: Position, b: Position): Float =
        (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x)
}
