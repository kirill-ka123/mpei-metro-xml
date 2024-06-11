package ru.mpei.metro.presentation.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import android.view.View
import ru.mpei.metro.R
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Position
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.model.Text
import ru.mpei.metro.presentation.map.model.SelectedStations
import kotlin.math.abs


private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 3.0f
private const val MIN_POS = -1000f
private const val MAX_POS = 1000f

private const val LINE_WIDTH = 2.5f
private const val TRANSITION_WIDTH = 6f
private const val TRANSITION_INSIDE_WIDTH = 4.2f
private const val OVERGROUND_TRANSITION_WIDTH = 1f
private const val TRANSITION_STATION_RADIUS = 1f
private const val STATION_CIRCLE_RADIUS = 2.2f
private const val STATION_CIRCLE_STROKE_WIDTH = 1f
private const val STATION_CLICKED_CIRCLE_RADIUS = 5.5f
private const val CLICK_AREA = 10f
private const val STATION_TEXT_SIZE = 6f

class MetroView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var metroGraph: MetroGraph? = null
    private var route: Route? = null
    private var selectedStations: SelectedStations? = null
    private var branchSplineConstructor: BranchSplineConstructor? = null

    private var scale = 1.0f
    private var posX = 0f
    private var posY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = INVALID_POINTER_ID
    private var canvasLastClickPosition: Position? = null

    private val linesPaint = Paint().apply {
        strokeWidth = LINE_WIDTH.dpToPx()
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }
    private val clickedStationPaint = Paint()
    private val stationPaint = Paint()
    private val stationTextPaint = Paint().apply {
        color = Color.BLACK
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textSize = STATION_TEXT_SIZE.spToPx()
    }
    private val transitionPaint = Paint()
    private val transitionInsidePaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = TRANSITION_INSIDE_WIDTH.dpToPx()
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(5f)
    }
    private val transitionOutsidePaint = Paint().apply {
        color = context.getColor(R.color.green_eagle)
        strokeWidth = TRANSITION_WIDTH.dpToPx()
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(5f)
    }
    private val groundTransitionPaint = Paint().apply {
        color = context.getColor(R.color.green_eagle)
        strokeWidth = OVERGROUND_TRANSITION_WIDTH.dpToPx()
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
    }
    private val transitionPath = Path()
    private val groundTransitionPath = Path()
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
                val newScale = (scale * detector.scaleFactor)
                if (scale != newScale) {
                    scale = newScale
                    invalidate()
                }
                return true
            }
        }
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

    fun setMetroGraph(metroGraph: MetroGraph) {
        this.metroGraph = metroGraph
        invalidate()
    }

    fun setRoute(route: Route?) {
        this.route = route
        invalidate()
    }

    fun setSelectedStations(selectedStations: SelectedStations) {
        this.selectedStations = selectedStations
        invalidate()
    }

    fun setBranchSplineConstructor(branchSplineConstructor: BranchSplineConstructor) {
        this.branchSplineConstructor = branchSplineConstructor
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
                    posX = (posX + x - lastTouchX)
                    posY = (posY + y - lastTouchY)
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
        metroGraph?.let { metroGraph ->
            canvas.scale(scale, scale)
            canvas.translate(posX * (1 / scale), posY * (1 / scale))

            metroGraph.branches.forEach { branch ->
                linesPaint.color = Color.parseColor(branch.hexColor)
                branchSplineConstructor?.drawBranch(
                    canvas = canvas,
                    paint = linesPaint,
                    branch = branch,
                )
            }
            canvas.drawStations(metroGraph.stations)
            canvas.drawTransitions(metroGraph)
            canvas.drawStationNames(metroGraph.texts)
            route?.let { route ->
                canvas.drawBlackout(metroGraph)
                canvas.drawRoute(route)
            }
            canvasLastClickPosition?.let { clickCoordinates ->
                canvas.drawClickedStation(metroGraph.stations, clickCoordinates)
            }
        }
    }

    private fun Canvas.drawClickedStation(
        stations: List<Station>,
        clickCoordinates: Position,
    ) {
        stations.forEach { station ->
            if (station.inClickArea(clickCoordinates)) {
                clickedStationPaint.color = Color.parseColor(station.hexColor)
                drawCircle(
                    station.position.x,
                    station.position.y,
                    STATION_CLICKED_CIRCLE_RADIUS.dpToPx(),
                    clickedStationPaint,
                )
                clickedStationPaint.color = Color.WHITE
                drawCircle(
                    station.position.x,
                    station.position.y,
                    STATION_CIRCLE_STROKE_WIDTH.dpToPx(),
                    clickedStationPaint,
                )
                return
            }
        }
    }

    private fun Canvas.drawStations(
        stations: List<Station>,
    ) {
        stations.forEach { station ->
            stationPaint.color = Color.parseColor(station.hexColor)
            drawCircle(
                station.position.x,
                station.position.y,
                STATION_CIRCLE_RADIUS.dpToPx(),
                stationPaint,
            )

            stationPaint.color = Color.WHITE
            drawCircle(
                station.position.x,
                station.position.y,
                STATION_CIRCLE_STROKE_WIDTH.dpToPx(),
                stationPaint,
            )
        }
    }

    private fun Canvas.drawStationNames(texts: List<Text>) {
        texts.forEach { text ->
            val lines = text.text.split("\n")
            val metrics = stationTextPaint.fontMetrics
            val lineHeight = metrics.descent - metrics.ascent + metrics.leading
            val textHeight = lineHeight * lines.size

            lines.forEachIndexed { index, line ->
                val textWidth = stationTextPaint.measureText(line)

                val (textX, baseY) = when (text.anchor) {
                    "a" -> text.position.x to text.position.y + textHeight / 2
                    "b" -> text.position.x - textWidth to text.position.y + textHeight / 2
                    "c" -> text.position.x - textWidth to text.position.y - textHeight / 2
                    "d" -> text.position.x to text.position.y - textHeight / 2
                    "e" -> text.position.x - textWidth / 2 to text.position.y + textHeight / 2
                    "f" -> text.position.x - textWidth to text.position.y
                    "g" -> text.position.x - textWidth / 2 to text.position.y - textHeight / 2
                    "h" -> text.position.x to text.position.y
                    else -> text.position.x to text.position.y
                }

                val textY = baseY + index * lineHeight
                drawText(line, textX, textY, stationTextPaint)
            }
        }
    }


    private fun Canvas.drawTransitions(metroGraph: MetroGraph) {
        metroGraph.groundTransitions.forEach { groundTransition ->
            val fromStation = metroGraph.stations.find { it.id == groundTransition.fromStationId }
            val toStation = metroGraph.stations.find { it.id == groundTransition.toStationId }
            if (fromStation != null && toStation != null) {
                drawGroundTransition(fromStation, toStation)
            }
        }
        metroGraph.undergroundTransitions.forEach { undergroundTransition ->
            val undergroundTransitionStations =
                undergroundTransition.stationIds.mapNotNull { stationId ->
                    metroGraph.stations.find { it.id == stationId }
                }
            drawUndergroundTransition(undergroundTransitionStations)
        }
    }

    private fun Canvas.drawUndergroundTransition(stations: List<Station>) {
        if (stations.size < 2) {
            return
        }
        transitionPath.reset()
        val transitionPositions = stations.map { transitionStation ->
            transitionStation.position
        }

        val convexHull = ConvexHullCalculator.calculateConvexHull(transitionPositions)
        transitionPath.apply {
            moveTo(convexHull[0].x, convexHull[0].y)
            for (i in 1 until convexHull.size) {
                val position = convexHull[i]
                lineTo(position.x, position.y)
            }
            close()
        }
        drawPath(transitionPath, transitionOutsidePaint)
        drawPath(transitionPath, transitionInsidePaint)

        stations.forEach { station ->
            transitionPaint.color = Color.parseColor(station.hexColor)
            drawCircle(
                station.position.x,
                station.position.y,
                TRANSITION_STATION_RADIUS.dpToPx(),
                transitionPaint,
            )
        }
    }

    private fun Canvas.drawGroundTransition(fromStations: Station, toStation: Station) {
        groundTransitionPath.reset()
        groundTransitionPath.moveTo(fromStations.position.x, fromStations.position.y)
        groundTransitionPath.lineTo(toStation.position.x, toStation.position.y)
        drawPath(groundTransitionPath, groundTransitionPaint)
    }

    private fun Canvas.drawBlackout(metroGraph: MetroGraph) {
        val maxX = metroGraph.stations.maxOf { it.position.x } / 0.1f
        val minX = -abs(metroGraph.stations.minOf { it.position.x } / 0.1f)
        val maxY = metroGraph.stations.maxOf { it.position.y } / 0.1f
        val minY = -abs(metroGraph.stations.minOf { it.position.y } / 0.1f)
        drawRect(minX, minY, maxX, maxY, blackoutPaint)
    }

    private fun Canvas.drawRoute(route: Route) {
        val branchSplineConstructor = branchSplineConstructor ?: return
        val routeStations = route.routeNodes.map { it.station }
        val branchWithStationsToDraw = routeStations.groupBy { station ->
            station.branchId
        }.mapNotNull {
            val branchId = it.key
            val stationsToDraw = it.value
            val branch = metroGraph?.branches?.find { branch ->
                branch.id == branchId
            } ?: return@mapNotNull null

            branch to stationsToDraw
        }
        for ((branch, stations) in branchWithStationsToDraw) {
            if (branchWithStationsToDraw.size < 2) {
                continue
            }
            linesPaint.color = Color.parseColor(branch.hexColor)
            branchSplineConstructor.drawSegmentOfBranch(
                canvas = this,
                paint = linesPaint,
                branch = branch,
                fromStation = stations.first(),
                toStation = stations.last(),
            )
        }
        drawStations(routeStations)
    }

    private fun Station.inClickArea(clickCoordinates: Position) =
        abs(position.x - clickCoordinates.x) < CLICK_AREA.dpToPx() &&
                abs(position.y - clickCoordinates.y) < CLICK_AREA.dpToPx()

    fun Float.dpToPx(): Float {
        val density = context.resources.displayMetrics.density
        return this * density
    }

    private fun Float.spToPx() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
}
