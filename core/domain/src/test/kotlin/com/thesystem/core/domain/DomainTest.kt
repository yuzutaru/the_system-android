package com.thesystem.core.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class XPCalculatorTest {
    @Test
    fun strengthXpUsesVolumeOver50() {
        val entry = SessionEntry(
            id = "1",
            exerciseId = "squat",
            kind = ExerciseKind.STRENGTH,
            sets = listOf(SetEntry(reps = 5, weightKg = 100.0)),
        )
        assertEquals(10, XPCalculator.entryXp(entry))
    }

    @Test
    fun strengthXpIsZeroForNoVolume() {
        val entry = SessionEntry("1", "squat", ExerciseKind.STRENGTH)
        assertEquals(0, XPCalculator.entryXp(entry))
    }

    @Test
    fun cardioUsesHighIntensityFactor() {
        assertEquals(12, XPCalculator.cardioXp(10.0, 150))
        assertEquals(10, XPCalculator.cardioXp(10.0, 100))
    }

    @Test
    fun goldIsHalfXp() {
        assertEquals(10, XPCalculator.gold(21))
    }

    @Test
    fun splitKeepsTotal() {
        val scores = XPCalculator.split(10, Attribute.STRENGTH, Attribute.ENDURANCE)
        assertEquals(10, scores.strength + scores.endurance)
        assertEquals(3, scores.endurance)
    }

    @Test
    fun splitWithoutSecondary() {
        val scores = XPCalculator.split(10, Attribute.STRENGTH, null)
        assertEquals(10, scores.strength)
        assertEquals(0, scores.endurance)
    }
}

class LevelCurveTest {
    @Test
    fun totalXpForLevelMatchesTable() {
        assertEquals(0, LevelCurve.totalXpForLevel(1))
        assertEquals(100, LevelCurve.totalXpForLevel(2))
        assertEquals(300, LevelCurve.totalXpForLevel(3))
        assertEquals(600, LevelCurve.totalXpForLevel(4))
        assertEquals(1000, LevelCurve.totalXpForLevel(5))
    }

    @Test
    fun levelForTotalXp() {
        assertEquals(1, LevelCurve.levelForTotalXp(0))
        assertEquals(1, LevelCurve.levelForTotalXp(99))
        assertEquals(2, LevelCurve.levelForTotalXp(100))
        assertEquals(3, LevelCurve.levelForTotalXp(300))
    }

    @Test
    fun epleyOneRepMax() {
        assertEquals(116.666, OneRepMax.epley(100.0, 5), 0.01)
    }
}

class CharacterTest {
    @Test
    fun defaultIsNovice() {
        assertEquals(CharacterClass.NOVICE, CharacterClass.resolve(AttributeScores.ZERO))
    }

    @Test
    fun warriorUnlock() {
        assertEquals(CharacterClass.WARRIOR, CharacterClass.resolve(AttributeScores(strength = 20)))
    }

    @Test
    fun berserkerBeatsWarrior() {
        assertEquals(
            CharacterClass.BERSERKER,
            CharacterClass.resolve(AttributeScores(strength = 40)),
        )
    }

    @Test
    fun paladinRequiresEndurance() {
        assertEquals(
            CharacterClass.PALADIN,
            CharacterClass.resolve(AttributeScores(strength = 20, endurance = 30)),
        )
    }

    @Test
    fun monkHybrid() {
        assertEquals(
            CharacterClass.MONK,
            CharacterClass.resolve(AttributeScores(agility = 15, vitality = 15)),
        )
    }

    @Test
    fun allocationSpendsPoints() {
        val character = Character(id = "c", unspentPoints = 3)
        val allocated = character.allocate(2, Attribute.STRENGTH)
        assertEquals(1, allocated?.unspentPoints)
        assertEquals(2, allocated?.allocated?.strength)
        assertNull(character.allocate(5, Attribute.AGILITY))
    }

    @Test
    fun levelUpGrantsPoints() {
        val character = Character(id = "c").award(300)
        assertEquals(3, character.level)
        assertTrue(character.unspentPoints >= 3)
    }
}
