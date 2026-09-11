package com.thesystem.core.data

import com.thesystem.core.database.dao.CharacterDao
import com.thesystem.core.database.entity.CharacterEntity
import com.thesystem.core.domain.AttributeScores
import com.thesystem.core.domain.Character
import com.thesystem.core.domain.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dao: CharacterDao,
) : CharacterRepository {

    override suspend fun load(): Character {
        val entity = dao.byId(CHARACTER_ID) ?: return Character(id = CHARACTER_ID)
        return Character(
            id = entity.id,
            totalXp = entity.totalXp,
            unspentPoints = entity.unspentPoints,
            allocated = AttributeScores(
                strength = entity.strength,
                endurance = entity.endurance,
                agility = entity.agility,
                vitality = entity.vitality,
            ),
        )
    }

    override suspend fun save(character: Character) {
        dao.upsert(
            CharacterEntity(
                id = character.id,
                totalXp = character.totalXp,
                unspentPoints = character.unspentPoints,
                strength = character.allocated.strength,
                endurance = character.allocated.endurance,
                agility = character.allocated.agility,
                vitality = character.allocated.vitality,
            ),
        )
    }

    private companion object {
        const val CHARACTER_ID = "local"
    }
}
