package com.thesystem.core.domain

enum class CharacterClass(val displayName: String) {
    NOVICE("Novice"),
    WARRIOR("Warrior"),
    ROGUE("Rogue"),
    MONK("Monk"),
    BERSERKER("Berserker"),
    PALADIN("Paladin"),
    ASSASSIN("Assassin"),
    RANGER("Ranger");

    companion object {
        fun resolve(attributes: AttributeScores): CharacterClass {
            val str = attributes.strength
            val end = attributes.endurance
            val agi = attributes.agility
            val vit = attributes.vitality

            val isWarrior = str >= 20
            val isRogue = agi >= 20

            var result = NOVICE
            if (isWarrior) result = WARRIOR
            if (isRogue) result = ROGUE
            if (agi >= 15 && vit >= 15) result = MONK
            if (isWarrior && str >= 40) result = BERSERKER
            if (isWarrior && end >= 30) result = PALADIN
            if (isRogue && agi >= 40) result = ASSASSIN
            if (isRogue && end >= 30) result = RANGER
            return result
        }
    }
}

data class Character(
    val id: String,
    val totalXp: Int = 0,
    val unspentPoints: Int = 0,
    val allocated: AttributeScores = AttributeScores.ZERO,
) {
    val level: Int get() = LevelCurve.levelForTotalXp(totalXp)

    val characterClass: CharacterClass get() = CharacterClass.resolve(allocated)

    fun award(xp: Int): Character {
        val previousLevel = level
        val updated = copy(totalXp = totalXp + xp)
        val gainedLevels = updated.level - previousLevel
        return updated.copy(unspentPoints = unspentPoints + gainedLevels * LevelCurve.POINTS_PER_LEVEL)
    }

    fun allocate(points: Int, attribute: Attribute): Character? {
        if (points <= 0 || points > unspentPoints) return null
        return copy(
            unspentPoints = unspentPoints - points,
            allocated = allocated.plus(attribute, points),
        )
    }
}
