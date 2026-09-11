package com.thesystem.core.data

import com.thesystem.core.domain.ExerciseKind
import com.thesystem.core.domain.SessionEntry
import com.thesystem.core.domain.SetEntry
import kotlinx.serialization.Serializable

@Serializable
data class SetEntryDto(
    val reps: Int,
    val weightKg: Double,
    val rpe: Double? = null,
)

@Serializable
data class SessionEntryDto(
    val id: String,
    val exerciseId: String,
    val kind: String,
    val sets: List<SetEntryDto> = emptyList(),
    val durationMin: Double = 0.0,
    val distanceKm: Double? = null,
    val avgHeartRate: Int? = null,
)

fun SessionEntry.toDto(): SessionEntryDto = SessionEntryDto(
    id = id,
    exerciseId = exerciseId,
    kind = kind.name,
    sets = sets.map { SetEntryDto(it.reps, it.weightKg, it.rpe) },
    durationMin = durationMin,
    distanceKm = distanceKm,
    avgHeartRate = avgHeartRate,
)

fun SessionEntryDto.toDomain(): SessionEntry = SessionEntry(
    id = id,
    exerciseId = exerciseId,
    kind = ExerciseKind.valueOf(kind),
    sets = sets.map { SetEntry(it.reps, it.weightKg, it.rpe) },
    durationMin = durationMin,
    distanceKm = distanceKm,
    avgHeartRate = avgHeartRate,
)
