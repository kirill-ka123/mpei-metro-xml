package ru.mpei.metro.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.mpei.metro.domain.model.Station

class StationConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromStation(station: Station): String {
        return gson.toJson(station)
    }

    @TypeConverter
    fun toStation(data: String): Station {
        return gson.fromJson(data, Station::class.java)
    }
}
