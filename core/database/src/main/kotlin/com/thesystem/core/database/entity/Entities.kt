package com.thesystem.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sessions")
data class WorkoutSessionEntity(
    @PrimaryKey val id: String,
    val questId: String?,
    val startedAt: Long,
    val endedAt: Long?,
    val xpEarned: Int,
    val goldEarned: Int,
    val entriesJson: String,
)

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val totalXp: Int,
    val unspentPoints: Int,
    val strength: Int,
    val endurance: Int,
    val agility: Int,
    val vitality: Int,
)
