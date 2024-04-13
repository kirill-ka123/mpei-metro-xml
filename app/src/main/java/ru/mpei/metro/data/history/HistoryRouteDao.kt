package ru.mpei.metro.data.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryRouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: HistoryRouteEntity)

    @Delete
    suspend fun deleteRoute(route: HistoryRouteEntity)

    @Query("SELECT * FROM history_route")
    fun getAllRoutes(): Flow<List<HistoryRouteEntity>>
}
