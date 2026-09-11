package com.thesystem.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thesystem.core.database.entity.CharacterEntity
import com.thesystem.core.database.entity.WorkoutSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {
    @Query("SELECT * FROM workout_sessions ORDER BY startedAt DESC")
    fun observeAll(): Flow<List<WorkoutSessionEntity>>

    @Query("SELECT * FROM workout_sessions ORDER BY startedAt DESC")
    suspend fun all(): List<WorkoutSessionEntity>

    @Upsert
    suspend fun upsert(entity: WorkoutSessionEntity)

    @Query("DELETE FROM workout_sessions WHERE id = :id")
    suspend fun delete(id: String)
}

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character WHERE id = :id LIMIT 1")
    suspend fun byId(id: String): CharacterEntity?

    @Upsert
    suspend fun upsert(entity: CharacterEntity)
}
