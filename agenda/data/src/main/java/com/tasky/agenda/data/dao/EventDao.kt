package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM eventEntity WHERE id=:eventId")
    suspend fun getEventById(eventId: String): EventEntity?

    @Query("SELECT * FROM eventEntity")
    suspend fun getEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM eventEntity WHERE `from` BETWEEN :startTime AND :endTime")
    suspend fun getEventsByTime(startTime: Long, endTime: Long): List<EventEntity>

    @Upsert
    suspend fun upsertEvent(eventEntity: EventEntity)

    @Upsert
    suspend fun upsertEvents(eventEntities: List<EventEntity>)

    @Query("DELETE FROM eventEntity WHERE id=:eventId")
    suspend fun deleteEvent(eventId: String)

    @Query("DELETE FROM eventEntity")
    suspend fun deleteEvents()

}