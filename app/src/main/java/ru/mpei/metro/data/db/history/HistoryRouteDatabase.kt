package ru.mpei.metro.data.db.history

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryRouteEntity::class],
    version = 2,
)
abstract class HistoryRouteDatabase: RoomDatabase() {
    abstract val historyRouteDao: HistoryRouteDao
}
