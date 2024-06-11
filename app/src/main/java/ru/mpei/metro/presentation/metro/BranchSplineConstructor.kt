package ru.mpei.metro.presentation.metro

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.domain.model.Branch
import ru.mpei.metro.domain.model.Position
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.sqrt

@MetroFragmentScope
class BranchSplineConstructor @Inject constructor(
    private val metroGraphProvider: MetroGraphProvider,
) : FragmentOnCreateViewListener, DefaultLifecycleObserver {
    private val branchPathInfo = HashMap<Branch, BranchStationsPathInfo>()

    override fun onCreateView(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
        val metroGraph = metroGraphProvider.getMetroGraph()
        metroGraph.branches.forEach { branch ->
            val branchStations = branch.stationIds.mapNotNull { stationId ->
                metroGraph.stations.find { it.id == stationId }
            }
            constructBranchSpline(
                branch,
                branchStations + if (branch.isBranchLooped) {
                    listOf(branchStations.first())
                } else {
                    emptyList()
                }
            )
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        branchPathInfo.clear()
    }

    fun drawBranch(canvas: Canvas, paint: Paint, branch: Branch) {
        val branchStationsPathInfo = branchPathInfo[branch]
        if (branchStationsPathInfo != null) {
            canvas.drawPath(branchStationsPathInfo.path, paint)
        }
    }

    fun drawSegmentOfBranch(
        canvas: Canvas,
        paint: Paint,
        branch: Branch,
        fromStation: Station,
        toStation: Station,
    ) {
        val branchStationsPathInfo = branchPathInfo[branch]
        if (branchStationsPathInfo != null) {
            val branchStations = branchStationsPathInfo.branchStations
            val fromIndex = branchStations.indexOfFirst { it.id == fromStation.id }
            val toIndex = branchStations.indexOfFirst { it.id == toStation.id }
            if (fromIndex == -1 || toIndex == -1 || abs(toIndex - fromIndex) < 1) {
                return
            }
            val (startStation, endStation) = if (fromIndex > toIndex) {
                toStation to fromStation
            } else {
                fromStation to toStation
            }
            val startStationPathLength = branchStationsPathInfo.stationToPathLength[startStation]
            val endStationPathLength = branchStationsPathInfo.stationToPathLength[endStation]
            if (startStationPathLength == null || endStationPathLength == null) {
                return
            }

            val pathMeasure = PathMeasure(branchStationsPathInfo.path, false)
            val clippedBranchPath = Path()
            pathMeasure.getSegment(
                minOf(startStationPathLength, endStationPathLength),
                maxOf(startStationPathLength, endStationPathLength),
                clippedBranchPath,
                true,
            )

            canvas.drawPath(clippedBranchPath, paint)
        }
    }

    private fun constructBranchSpline(branch: Branch, branchStations: List<Station>) {
        if (branchStations.size < 2) {
            return
        }
        val path = Path()
        val stationToPathLength = HashMap<Station, Float>()

        val initialStation = branchStations.first()
        val initialPosition = initialStation.position
        path.moveTo(initialPosition.x, initialPosition.y)
        stationToPathLength[initialStation] = 0f

        if (branchStations.size == 2) {
            val finalStation = branchStations.last()
            val finalPosition = finalStation.position
            path.lineTo(finalPosition.x, finalPosition.y)
            stationToPathLength[finalStation] = PathMeasure(path, false).length
        } else {
            val controlPoints = calculateControlPoints(branchStations)

            for (i in 0 until branchStations.size - 1) {
                val station = branchStations[i + 1]
                val p1 = station.position
                val c0 = controlPoints[i * 2]
                val c1 = controlPoints[i * 2 + 1]

                path.cubicTo(c0.x, c0.y, c1.x, c1.y, p1.x, p1.y)

                if (!stationToPathLength.containsKey(station)) {
                    stationToPathLength[station] = PathMeasure(path, false).length
                }
            }
        }

        if (branch.isBranchLooped) {
            path.close()
        }

        branchPathInfo[branch] = BranchStationsPathInfo(path, branchStations, stationToPathLength)
    }

    private fun calculateControlPoints(stations: List<Station>): List<Position> {
        val controlPoints = mutableListOf<Position>()

        for (i in 0 until stations.size - 1) {
            val p0 = stations[i].position
            val p1 = stations[i + 1].position
            val p2 = if (i < stations.size - 2) {
                stations[i + 2].position
            } else {
                p1
            }

            val d1 = distance(p0, p1)
            val d2 = distance(p1, p2)

            val c1 = if (i == 0) {
                Position(
                    p0.x + (p1.x - p0.x) / 3,
                    p0.y + (p1.y - p0.y) / 3
                )
            } else {
                Position(
                    p0.x + (p1.x - stations[i - 1].position.x) / (6 * d1) * d1,
                    p0.y + (p1.y - stations[i - 1].position.y) / (6 * d1) * d1
                )
            }

            val c2 = if (i == stations.size - 2) {
                Position(
                    p1.x - (p2.x - p1.x) / 3,
                    p1.y - (p2.y - p1.y) / 3
                )
            } else {
                Position(
                    p1.x - (stations[i + 2].position.x - p0.x) / (6 * d2) * d1,
                    p1.y - (stations[i + 2].position.y - p0.y) / (6 * d2) * d1
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

    private class BranchStationsPathInfo(
        val path: Path,
        val branchStations: List<Station>,
        val stationToPathLength: Map<Station, Float>,
    )
}
