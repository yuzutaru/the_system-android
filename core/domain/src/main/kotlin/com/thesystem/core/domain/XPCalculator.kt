package com.thesystem.core.domain

import kotlin.math.floor

object XPCalculator {
    const val STRENGTH_VOLUME_PER_XP = 50.0
    const val CARDIO_HIGH_INTENSITY_THRESHOLD = 140
    const val CARDIO_HIGH_INTENSITY_FACTOR = 1.2
    const val SECONDARY_SHARE = 0.3

    fun entryXp(entry: SessionEntry): Int = when (entry.kind) {
        ExerciseKind.STRENGTH -> strengthXp(entry.volume)
        ExerciseKind.CARDIO -> cardioXp(entry.durationMin, entry.avgHeartRate)
        ExerciseKind.MOBILITY -> mobilityXp(entry.durationMin)
    }

    fun strengthXp(volume: Double): Int {
        if (volume <= 0.0) return 0
        return maxOf(1, floor(volume / STRENGTH_VOLUME_PER_XP).toInt())
    }

    fun cardioXp(durationMin: Double, avgHeartRate: Int?): Int {
        if (durationMin <= 0.0) return 0
        val factor = if ((avgHeartRate ?: 0) >= CARDIO_HIGH_INTENSITY_THRESHOLD) {
            CARDIO_HIGH_INTENSITY_FACTOR
        } else {
            1.0
        }
        return maxOf(1, floor(durationMin * factor).toInt())
    }

    fun mobilityXp(durationMin: Double): Int {
        if (durationMin <= 0.0) return 0
        return maxOf(1, floor(durationMin).toInt())
    }

    fun sessionXp(entries: List<SessionEntry>): Int = entries.sumOf { entryXp(it) }

    fun gold(xp: Int): Int = xp / 2

    fun split(xp: Int, primary: Attribute, secondary: Attribute?): AttributeScores {
        if (secondary == null || secondary == primary) {
            return AttributeScores.ZERO.plus(primary, xp)
        }
        val secondaryXp = floor(xp * SECONDARY_SHARE).toInt()
        return AttributeScores.ZERO
            .plus(primary, xp - secondaryXp)
            .plus(secondary, secondaryXp)
    }
}
