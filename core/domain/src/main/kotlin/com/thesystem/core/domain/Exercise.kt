package com.thesystem.core.domain

import java.time.Instant

enum class ExerciseKind { STRENGTH, CARDIO, MOBILITY }

data class Exercise(
    val id: String,
    val name: String,
    val kind: ExerciseKind,
    val primary: Attribute,
    val secondary: Attribute? = null,
)

data class SetEntry(
    val reps: Int,
    val weightKg: Double,
    val rpe: Double? = null,
) {
    val volume: Double get() = reps * weightKg
}

data class SessionEntry(
    val id: String,
    val exerciseId: String,
    val kind: ExerciseKind,
    val sets: List<SetEntry> = emptyList(),
    val durationMin: Double = 0.0,
    val distanceKm: Double? = null,
    val avgHeartRate: Int? = null,
) {
    val volume: Double get() = sets.sumOf { it.volume }
}

data class WorkoutSession(
    val id: String,
    val questId: String? = null,
    val startedAt: Instant,
    val endedAt: Instant? = null,
    val entries: List<SessionEntry>,
    val xpEarned: Int,
    val goldEarned: Int,
)
