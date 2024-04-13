package ru.mpei.metro.data.history

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryRouteEntity::class],
    version = 1,
)
abstract class MetroDatabase: RoomDatabase() {
    abstract val historyRouteDao: HistoryRouteDao
}
