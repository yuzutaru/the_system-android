package com.thesystem.workout

import com.thesystem.core.domain.Character
import com.thesystem.core.domain.CharacterRepository
import com.thesystem.core.domain.ExerciseKind
import com.thesystem.core.domain.SessionEntry
import com.thesystem.core.domain.SetEntry
import com.thesystem.core.domain.WorkoutRepository
import com.thesystem.core.domain.WorkoutSession
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private class InMemoryWorkoutRepository : WorkoutRepository {
    val sessions = mutableListOf<WorkoutSession>()
    override suspend fun save(session: WorkoutSession) {
        sessions += session
    }

    override suspend fun allSessions(): List<WorkoutSession> = sessions

    override suspend fun deleteSession(id: String) {
        sessions.removeAll { it.id == id }
    }
}

private class InMemoryCharacterRepository : CharacterRepository {
    private var character = Character(id = "local")
    override suspend fun load(): Character = character
    override suspend fun save(character: Character) {
        this.character = character
    }
}

class LogWorkoutUseCaseTest {
    @Test
    fun loggingAwardsXpAndGold() = runTest {
        val workouts = InMemoryWorkoutRepository()
        val characters = InMemoryCharacterRepository()
        val useCase = LogWorkoutUseCase(workouts, characters)

        val entry = SessionEntry(
            id = "e1",
            exerciseId = "barbell-squat",
            kind = ExerciseKind.STRENGTH,
            sets = listOf(SetEntry(reps = 10, weightKg = 100.0)),
        )

        val result = useCase(entries = listOf(entry))

        assertEquals(20, result.session.xpEarned)
        assertEquals(10, result.session.goldEarned)
        assertEquals(20, characters.load().totalXp)
    }

    @Test
    fun attributeSplitIsReturned() = runTest {
        val workouts = InMemoryWorkoutRepository()
        val characters = InMemoryCharacterRepository()
        val useCase = LogWorkoutUseCase(workouts, characters)

        val entry = SessionEntry(
            id = "e1",
            exerciseId = "deadlift",
            kind = ExerciseKind.STRENGTH,
            sets = listOf(SetEntry(reps = 5, weightKg = 100.0)),
        )

        val result = useCase(entries = listOf(entry))

        assertEquals(
            result.session.xpEarned,
            result.attributeXp.strength + result.attributeXp.endurance,
        )
        assertTrue(result.attributeXp.strength > result.attributeXp.endurance)
    }
}
