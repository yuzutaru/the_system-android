package com.thesystem.core.domain

interface WorkoutRepository {
    suspend fun save(session: WorkoutSession)
    suspend fun allSessions(): List<WorkoutSession>
    suspend fun deleteSession(id: String)
}

interface CharacterRepository {
    suspend fun load(): Character
    suspend fun save(character: Character)
}
