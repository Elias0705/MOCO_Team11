package com.example.mypinapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PinDao {
    @Query("SELECT * FROM pins ORDER BY timestamp DESC")
    fun getAllPins(): Flow<List<Pin>>

    @Query("SELECT * FROM pins WHERE groupId = :groupId")
    fun getPinsByGroup(groupId: Long): Flow<List<Pin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPin(pin: Pin): Long

    @Update
    suspend fun updatePin(pin: Pin)

    @Delete
    suspend fun deletePin(pin: Pin)

    @Query("SELECT * FROM pin_groups")
    fun getAllGroups(): Flow<List<PinGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: PinGroup): Long

    @Delete
    suspend fun deleteGroup(group: PinGroup)
}
