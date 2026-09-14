package com.thesystem.workout

import com.thesystem.core.domain.Attribute
import com.thesystem.core.domain.AttributeScores
import com.thesystem.core.domain.CharacterRepository
import com.thesystem.core.domain.Exercise
import com.thesystem.core.domain.ExerciseCatalog
import com.thesystem.core.domain.SessionEntry
import com.thesystem.core.domain.WorkoutRepository
import com.thesystem.core.domain.WorkoutSession
import com.thesystem.core.domain.XPCalculator
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

data class LogResult(
    val session: WorkoutSession,
    val attributeXp: AttributeScores,
    val previousLevel: Int,
    val newLevel: Int,
) {
    val leveledUp: Boolean get() = newLevel > previousLevel
}

class LogWorkoutUseCase @Inject constructor(
    private val workouts: WorkoutRepository,
    private val characters: CharacterRepository,
) {
    suspend operator fun invoke(
        entries: List<SessionEntry>,
        questId: String? = null,
        id: String = UUID.randomUUID().toString(),
        startedAt: Instant = Instant.now(),
        endedAt: Instant? = null,
        catalog: List<Exercise> = ExerciseCatalog.samples,
    ): LogResult {
        val xp = XPCalculator.sessionXp(entries)
        val session = WorkoutSession(
            id = id,
            questId = questId,
            startedAt = startedAt,
            endedAt = endedAt,
            entries = entries,
            xpEarned = xp,
            goldEarned = XPCalculator.gold(xp),
        )

        val byId = catalog.associateBy { it.id }
        var attributeXp = AttributeScores.ZERO
        for (entry in entries) {
            val exercise = byId[entry.exerciseId] ?: continue
            val split = XPCalculator.split(
                xp = XPCalculator.entryXp(entry),
                primary = exercise.primary,
                secondary = exercise.secondary,
            )
            attributeXp = attributeXp
                .plus(Attribute.STRENGTH, split.strength)
                .plus(Attribute.ENDURANCE, split.endurance)
                .plus(Attribute.AGILITY, split.agility)
                .plus(Attribute.VITALITY, split.vitality)
        }

        val character = characters.load()
        val previousLevel = character.level
        val updated = character.award(xp)

        workouts.save(session)
        characters.save(updated)

        return LogResult(
            session = session,
            attributeXp = attributeXp,
            previousLevel = previousLevel,
            newLevel = updated.level,
        )
    }
}
