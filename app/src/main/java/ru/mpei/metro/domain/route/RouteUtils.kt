package ru.mpei.metro.domain.route

import ru.mpei.metro.domain.model.Route
import java.util.Calendar

fun Route.constructFullTimeOfRoute() =
    "${constructStartTimeOfRoute()} – ${constructEndTimeOfRoute()}"

fun constructStartTimeOfRoute(): String {
    val calendar = Calendar.getInstance()
    val startHour = calendar.get(Calendar.HOUR_OF_DAY)
    val startMinute = calendar.get(Calendar.MINUTE)

    return String.format("%02d:%02d", startHour, startMinute)
}

fun Route.constructEndTimeOfRoute() = constructTimeOfRoute(totalTime)

fun constructTimeOfRoute(time: Int): String {
    val calendar = Calendar.getInstance()
    val startHour = calendar.get(Calendar.HOUR_OF_DAY)
    val startMinute = calendar.get(Calendar.MINUTE)

    val totalMinutes = time / 60
    val durationHours = totalMinutes / 60
    val durationMinutes = totalMinutes % 60

    var endHour = startHour + durationHours
    var endMinute = startMinute + durationMinutes

    if (endMinute >= 60) {
        endMinute -= 60
        endHour += 1
    }

    if (endHour >= 24) {
        endHour -= 24
    }
    return String.format("%02d:%02d", endHour, endMinute)
}
