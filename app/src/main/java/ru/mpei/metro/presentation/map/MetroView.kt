package ru.mpei.metro.presentation.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.ColorInt
import ru.mpei.metro.R
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.domain.model.Position
import ru.mpei.metro.domain.model.Road
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.model.Transition
import ru.mpei.metro.presentation.map.model.SelectedStations
import kotlin.math.abs

private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 3.0f
private const val MIN_POS = -1000f
private const val MAX_POS = 1000f

private const val LINE_WIDTH = 8f
private const val TRANSITION_WIDTH = 13f
private const val TRANSITION_INSIDE_WIDTH = 8f
private const val TRANSITION_STATION_RADIUS = 3f
private const val STATION_CIRCLE_RADIUS = 6f
private const val STATION_CIRCLE_STROKE_WIDTH = 3f
private const val STATION_CLICKED_CIRCLE_RADIUS = 15f
private const val CLICK_AREA = 25f
private const val STATION_TEXT_SIZE = 25f

class MetroView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var city: City? = null
    private var route: List<Road>? = null
    private var selectedStations: SelectedStations? = null

    private var scale = 1.0f
    private var posX = 0f
    private var posY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = INVALID_POINTER_ID
    private var canvasLastClickPosition: Position? = null

    private val linesPaint = Paint().apply {
        strokeWidth = LINE_WIDTH
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val linesPath = Path()
    private val clickedStationPaint = Paint()
    private val stationsPaint = Paint()
    private val transitionPaint = Paint()
    private val transitionInsidePaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = TRANSITION_INSIDE_WIDTH
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val transitionOutsidePaint = Paint().apply {
        color = context.getColor(R.color.green_eagle)
        strokeWidth = TRANSITION_WIDTH
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val transitionPath = Path()
    private val blackoutPaint = Paint().apply {
        // Sets alpha == 0.5.
        color = Color.BLACK and 0x00111111 or 0x7F000000
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(event: MotionEvent): Boolean {
            if (route != null) {
                return true
            }
            val clickPositionX = event.x / scale - posX * (1 / scale)
            val clickPositionY = event.y / scale - posY * (1 / scale)
            canvasLastClickPosition = Position(clickPositionX, clickPositionY)
            invalidate()
            return true
        }
    }
    private val gestureDetector = GestureDetector(context, gestureListener)
    private val scaleGestureListener =
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val newScale = (scale * detector.scaleFactor).coerceIn(MIN_SCALE, MAX_SCALE)
                if (scale != newScale) {
                    scale = newScale
                    invalidate()
                }
                return true
            }
        }
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

    fun setCity(city: City) {
        this.city = city
        invalidate()
    }

    fun setRoute(route: List<Road>?) {
        this.route = route
        invalidate()
    }

    fun setSelectedStations(selectedStations: SelectedStations) {
        this.selectedStations = selectedStations
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        val action = event.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                activePointerId = event.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = event.findPointerIndex(activePointerId)
                val x = event.getX(pointerIndex)
                val y = event.getY(pointerIndex)

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!scaleGestureDetector.isInProgress) {
                    posX = (posX + x - lastTouchX).coerceIn(MIN_POS, MAX_POS)
                    posY = (posY + y - lastTouchY).coerceIn(MIN_POS, MAX_POS)
                    invalidate()
                }
                lastTouchX = x
                lastTouchY = y
            }

            MotionEvent.ACTION_UP -> {
                activePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_CANCEL -> {
                activePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = (event.action and MotionEvent.ACTION_POINTER_INDEX_MASK
                        shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                val pointerId = event.getPointerId(pointerIndex)
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    lastTouchX = event.getX(newPointerIndex)
                    lastTouchY = event.getY(newPointerIndex)
                    activePointerId = event.getPointerId(newPointerIndex)
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        city?.let { city ->
            canvas.scale(scale, scale)
            canvas.translate(posX * (1 / scale), posY * (1 / scale))

            city.branches.forEach { branch ->
                val branchColor = Color.parseColor(branch.color)
                val branchStations = branch.stationsIds.mapNotNull { stationId ->
                    city.stations.find { it.id == stationId }
                }
                canvas.drawLines(
                    branchStations = branchStations,
                    branchColor = branchColor,
                )
            }
            canvasLastClickPosition?.let { clickCoordinates ->
                canvas.drawClickedStation(
                    stations = city.stations,
                    clickCoordinates = clickCoordinates,
                )
            }
            canvas.drawStations(stations = city.stations)
            canvas.drawTransitions(city = city, transitions = city.transitions)
            route?.let { route ->
                canvas.drawBlackout(city)
                canvas.drawRoute(route)
            }
        }
    }

    private fun Canvas.drawLines(
        branchStations: List<Station>,
        @ColorInt
        branchColor: Int,
    ) {
        linesPath.reset()
        linesPath.moveTo(branchStations.first().position.x, branchStations.first().position.y)
        branchStations.forEach { station ->
            linesPath.lineTo(station.position.x, station.position.y)
        }
        linesPaint.color = branchColor
        drawPath(linesPath, linesPaint)
    }

    private fun Canvas.drawClickedStation(
        stations: List<Station>,
        clickCoordinates: Position,
    ) {
        stations.forEach { station ->
            if (station.inClickArea(clickCoordinates)) {
                clickedStationPaint.color = Color.parseColor(station.branch.color)
                drawCircle(
                    station.position.x,
                    station.position.y,
                    STATION_CLICKED_CIRCLE_RADIUS,
                    clickedStationPaint,
                )
                return
            }
        }
    }

    private fun Canvas.drawSelectedStation(
        selectedStation: Station,
    ) {
        val paint = Paint()
        paint.color = Color.parseColor(selectedStation.branch.color)
        drawCircle(
            selectedStation.position.x,
            selectedStation.position.y,
            STATION_CLICKED_CIRCLE_RADIUS,
            paint,
        )
    }

    private fun Canvas.drawStations(
        stations: List<Station>,
    ) {
        stations.forEach { station ->
            stationsPaint.color = Color.parseColor(station.branch.color)
            drawCircle(
                station.position.x,
                station.position.y,
                STATION_CIRCLE_RADIUS,
                stationsPaint,
            )

            stationsPaint.color = Color.WHITE
            drawCircle(
                station.position.x,
                station.position.y,
                STATION_CIRCLE_STROKE_WIDTH,
                stationsPaint,
            )

            stationsPaint.color = context.getColor(R.color.black)
            stationsPaint.textSize = STATION_TEXT_SIZE
            drawText(
                station.name,
                station.position.x,
                station.position.y,
                stationsPaint,
            )
        }
    }

    private fun Canvas.drawTransitions(city: City, transitions: List<Transition>) {
        transitions.forEach { transaction ->
            val transactionStations = transaction.stationsIds.mapNotNull { stationId ->
                city.stations.find { it.id == stationId }
            }
            transitionPath.reset()
            transitionPath.moveTo(
                transactionStations.first().position.x,
                transactionStations.first().position.y
            )
            transactionStations.forEach { station ->
                transitionPath.lineTo(station.position.x, station.position.y)
            }
            drawPath(transitionPath, transitionOutsidePaint)
            drawPath(transitionPath, transitionInsidePaint)
            transactionStations.forEach { station ->
                transitionPaint.color = Color.parseColor(station.branch.color)
                drawCircle(
                    station.position.x,
                    station.position.y,
                    TRANSITION_STATION_RADIUS,
                    transitionPaint,
                )
            }
        }
    }

    private fun Canvas.drawBlackout(city: City) {
        val maxX = city.stations.maxOf { it.position.x } / 0.1f
        val minX = -abs(city.stations.minOf { it.position.x } / 0.1f)
        val maxY = city.stations.maxOf { it.position.y } / 0.1f
        val minY = -abs(city.stations.minOf { it.position.y } / 0.1f)
        drawRect(minX, minY, maxX, maxY, blackoutPaint)
    }

    private fun Canvas.drawRoute(route: List<Road>) {
        val routeStations = route.map { it.station }
        val branchStations = routeStations.groupBy { it.branch }
        val transactions = routeStations.groupBy { it.transaction }.mapNotNull { it.key }
        for ((branch, stations) in branchStations) {
            drawLines(
                branchStations = stations,
                branchColor = Color.parseColor(branch.color),
            )
        }
        drawStations(route.map { it.station })
        city?.let { city ->
            drawTransitions(city, transactions)
        }
    }

    private fun Station.inClickArea(clickCoordinates: Position) =
        abs(position.x - clickCoordinates.x) < CLICK_AREA &&
                abs(position.y - clickCoordinates.y) < CLICK_AREA
}
