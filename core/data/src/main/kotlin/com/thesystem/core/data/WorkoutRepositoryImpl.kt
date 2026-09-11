package com.thesystem.core.data

import com.thesystem.core.database.dao.WorkoutSessionDao
import com.thesystem.core.database.entity.WorkoutSessionEntity
import com.thesystem.core.domain.SessionEntry
import com.thesystem.core.domain.WorkoutRepository
import com.thesystem.core.domain.WorkoutSession
import javax.inject.Inject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutSessionDao,
    private val json: Json,
) : WorkoutRepository {

    override suspend fun save(session: WorkoutSession) {
        dao.upsert(session.toEntity())
    }

    override suspend fun allSessions(): List<WorkoutSession> =
        dao.all().map { it.toDomain() }

    override suspend fun deleteSession(id: String) = dao.delete(id)

    private fun WorkoutSession.toEntity() = WorkoutSessionEntity(
        id = id,
        questId = questId,
        startedAt = startedAt.toEpochMilli(),
        endedAt = endedAt?.toEpochMilli(),
        xpEarned = xpEarned,
        goldEarned = goldEarned,
        entriesJson = json.encodeToString(entries.map { it.toDto() }),
    )

    private fun WorkoutSessionEntity.toDomain() = WorkoutSession(
        id = id,
        questId = questId,
        startedAt = java.time.Instant.ofEpochMilli(startedAt),
        endedAt = endedAt?.let { java.time.Instant.ofEpochMilli(it) },
        entries = json.decodeFromString<List<SessionEntryDto>>(entriesJson).map { it.toDomain() },
        xpEarned = xpEarned,
        goldEarned = goldEarned,
    )
}

fun entriesFromJson(json: Json, raw: String): List<SessionEntry> =
    json.decodeFromString<List<SessionEntryDto>>(raw).map { it.toDomain() }
