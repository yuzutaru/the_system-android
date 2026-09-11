package com.thesystem.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thesystem.core.database.dao.CharacterDao
import com.thesystem.core.database.dao.WorkoutSessionDao
import com.thesystem.core.database.entity.CharacterEntity
import com.thesystem.core.database.entity.WorkoutSessionEntity

@Database(
    entities = [WorkoutSessionEntity::class, CharacterEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class TheSystemDatabase : RoomDatabase() {
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun characterDao(): CharacterDao
}
