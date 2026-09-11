package com.thesystem.core.domain

object LevelCurve {
    const val POINTS_PER_LEVEL = 3

    fun xpToNext(level: Int): Int {
        if (level < 1) return 0
        return 100 * level
    }

    fun totalXpForLevel(level: Int): Int {
        if (level < 1) return 0
        return 100 * level * (level - 1) / 2
    }

    fun levelForTotalXp(xp: Int): Int {
        if (xp <= 0) return 1
        var level = 1
        while (totalXpForLevel(level + 1) <= xp) {
            level++
        }
        return level
    }

    fun progressToNextLevel(totalXp: Int): Pair<Int, Int> {
        val level = levelForTotalXp(totalXp)
        val base = totalXpForLevel(level)
        return (totalXp - base) to xpToNext(level)
    }
}

object OneRepMax {
    fun epley(weightKg: Double, reps: Int): Double {
        if (reps < 1) return 0.0
        return weightKg * (1 + reps / 30.0)
    }
}
