package ru.mpei.metro.presentation.map.bottomsheet.detail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ru.mpei.metro.R
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.RouteNode
import ru.mpei.metro.domain.model.Station

private const val EDGE_STATION_CIRCLE_RADIUS = 35f
private const val EDGE_STATION_TEXT_SIZE = 31f
private const val STATION_CIRCLE_RADIUS = 15f
private const val STATION_TEXT_SIZE = 25f

private const val LARGE_OFFSET_BETWEEN_STATIONS = 140f
private const val SMALL_OFFSET_BETWEEN_STATIONS = 90f

private const val LINE_WIDTH = 5f

private const val EDGE_STATION_CIRCLE_STROKE_WIDTH = 15f

private const val STATION_TEXT_OFFSET = 30f

class RouteInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var route: Route? = null
    private val edgeStations: List<Station>
        get() = (route?.routeNodes ?: emptyList()).getEdgeStations()
    private val finalStation: Station
        get() = (route?.routeNodes ?: emptyList()).last().station

    private val lineColor = ContextCompat.getColor(context, R.color.grey)
    private val edgeStationTextColor = ContextCompat.getColor(context, R.color.green_eagle)
    private val stationTextColor = ContextCompat.getColor(context, R.color.grey)

    private var timeInfoEndCoordinate = 0f

    private val defaultStationTextPaint: Paint = Paint()
        get() = field.apply {
            route?.let {
                color = stationTextColor
                textSize = STATION_TEXT_SIZE
            }
        }
    private val edgeStationTextPaint: Paint = Paint()
        get() = field.apply {
            route?.let {
                color = edgeStationTextColor
                textSize = EDGE_STATION_TEXT_SIZE
            }
        }
    private val intermediateLinePaint = Paint().apply {
        color = lineColor
        strokeWidth = LINE_WIDTH
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }
    private val stationCirclePaint = Paint()

    private val timeInfoTextBounds = Rect()
    private val stationNameTextBounds = Rect()

    fun setRoute(route: Route?) {
        this.route = route
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (route == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
        val route = route ?: return
        var maxHeight = 0f
        val maxWidth: Float
        var maxTimeInfoEndCoordinate = 0f
        var maxStationNameEndCoordinate = 0f
        route.routeNodes.forEachIndexed { index, routeNode ->
            val nextStation = route.routeNodes.getOrNull(index + 1)?.station
            maxHeight += getDistanceToNextStation(routeNode.station, nextStation)

            val textPaint = routeNode.station.getStationTextPaint()
            val radius = routeNode.station.getRadius()
            val timeInfo = routeNode.achieveTime.toString()
            textPaint.getTextBounds(timeInfo, 0, timeInfo.length, timeInfoTextBounds)
            val stationName = routeNode.station.name
            textPaint.getTextBounds(stationName, 0, stationName.length, stationNameTextBounds)
            val timeInfoEndCoordinate = timeInfoTextBounds.width() + STATION_TEXT_OFFSET + radius
            if (timeInfoEndCoordinate > maxTimeInfoEndCoordinate) {
                maxTimeInfoEndCoordinate = timeInfoEndCoordinate
            }
            val stationNameEndCoordinate =
                stationNameTextBounds.width() + STATION_TEXT_OFFSET + radius
            if (stationNameEndCoordinate > maxStationNameEndCoordinate) {
                maxStationNameEndCoordinate = stationNameEndCoordinate
            }
        }
        maxWidth = maxTimeInfoEndCoordinate + maxStationNameEndCoordinate
        maxHeight += EDGE_STATION_CIRCLE_RADIUS * 2

        timeInfoEndCoordinate = maxTimeInfoEndCoordinate
        val width = resolveSizeAndState(maxWidth.toInt(), widthMeasureSpec, 0)
        val height = resolveSizeAndState(maxHeight.toInt(), heightMeasureSpec, 0)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val route = route ?: return
        var cursorY = EDGE_STATION_CIRCLE_RADIUS

        route.routeNodes.forEachIndexed { index, routeNode ->
            val nextStation = route.routeNodes.getOrNull(index + 1)?.station
            val radius = routeNode.station.getRadius()
            val radiusNextStation = nextStation?.getRadius() ?: 0f
            val distanceToNextStation = getDistanceToNextStation(routeNode.station, nextStation)
            if (routeNode.station !in edgeStations || nextStation !in edgeStations) {
                canvas.drawLine(
                    timeInfoEndCoordinate,
                    cursorY + radius,
                    timeInfoEndCoordinate,
                    cursorY + distanceToNextStation - radiusNextStation,
                    intermediateLinePaint
                )
            }
            canvas.drawStationCircle(
                station = routeNode.station,
                radius = radius,
                x = timeInfoEndCoordinate,
                y = cursorY,
            )
            canvas.drawStationInfoText(
                routeNode = routeNode,
                radius = radius,
                x = timeInfoEndCoordinate,
                y = cursorY,
            )
            cursorY += distanceToNextStation
        }
    }

    private fun Canvas.drawStationCircle(
        station: Station,
        radius: Float,
        x: Float,
        y: Float,
    ) {
        val stationCircleStrokeWidth = station.getCircleStrokeWidth()
        stationCirclePaint.apply {
            color = Color.parseColor(station.branch.hexColor)
            if (stationCircleStrokeWidth != 0f) {
                strokeWidth = stationCircleStrokeWidth
                style = Paint.Style.STROKE
            } else {
                style = Paint.Style.FILL
            }
        }
        drawCircle(
            x,
            y,
            radius - stationCircleStrokeWidth,
            stationCirclePaint,
        )
    }

    private fun Canvas.drawStationInfoText(
        routeNode: RouteNode,
        radius: Float,
        x: Float,
        y: Float,
    ) {
        val textPaint = routeNode.station.getStationTextPaint()
        val timeInfo = routeNode.achieveTime.toString()
        textPaint.getTextBounds(timeInfo, 0, timeInfo.length, timeInfoTextBounds)
        drawText(
            timeInfo,
            x - radius - timeInfoTextBounds.width() - STATION_TEXT_OFFSET,
            y - timeInfoTextBounds.exactCenterY(),
            textPaint,
        )
        val stationName = routeNode.station.name
        textPaint.getTextBounds(stationName, 0, stationName.length, stationNameTextBounds)
        drawText(
            stationName,
            x + radius + STATION_TEXT_OFFSET,
            y - stationNameTextBounds.exactCenterY(),
            textPaint,
        )
    }

    private fun List<RouteNode>.getEdgeStations(): List<Station> {
        val edgeStations = mutableSetOf(
            first().station,
            last().station,
        )
        forEachIndexed { index, routeNode ->
            val station = routeNode.station
            val nextStation = getOrNull(index + 1)?.station
            if (nextStation != null && station.branch != nextStation.branch) {
                edgeStations.add(station)
                edgeStations.add(nextStation)
            }
        }

        return edgeStations.toList()
    }

    private fun Station.getRadius() = when (this) {
        in edgeStations -> EDGE_STATION_CIRCLE_RADIUS
        else -> STATION_CIRCLE_RADIUS
    }

    private fun Station.getCircleStrokeWidth() =
        if (this in edgeStations && this != finalStation) {
            EDGE_STATION_CIRCLE_STROKE_WIDTH
        } else {
            0f
        }

    private fun getDistanceToNextStation(station: Station, nextStation: Station?) = when {
        nextStation == null -> 0f
        station in edgeStations -> LARGE_OFFSET_BETWEEN_STATIONS
        nextStation in edgeStations -> LARGE_OFFSET_BETWEEN_STATIONS
        else -> SMALL_OFFSET_BETWEEN_STATIONS
    }

    private fun Station.getStationTextPaint() = if (this in edgeStations) {
        edgeStationTextPaint
    } else {
        defaultStationTextPaint
    }
}
