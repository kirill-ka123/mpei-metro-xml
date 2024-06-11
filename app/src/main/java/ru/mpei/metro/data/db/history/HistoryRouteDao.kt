package ru.mpei.metro.data.db.history

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryRouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: HistoryRouteEntity)

    @Delete
    suspend fun deleteRoute(route: HistoryRouteEntity)

    @Query("DELETE FROM history_routes WHERE id = (SELECT id FROM history_routes ORDER BY timestamp ASC LIMIT 1)")
    fun deleteOldestRoute()

    @Query("SELECT * FROM history_routes ORDER BY timestamp DESC")
    fun getAllRoutes(): LiveData<List<HistoryRouteEntity>>
}
