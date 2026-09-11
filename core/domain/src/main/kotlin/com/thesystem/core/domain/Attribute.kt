package com.thesystem.core.domain

enum class Attribute(val code: String, val displayName: String) {
    STRENGTH("STR", "Strength"),
    ENDURANCE("END", "Endurance"),
    AGILITY("AGI", "Agility"),
    VITALITY("VIT", "Vitality"),
}

data class AttributeScores(
    val strength: Int = 0,
    val endurance: Int = 0,
    val agility: Int = 0,
    val vitality: Int = 0,
) {
    operator fun get(attribute: Attribute): Int = when (attribute) {
        Attribute.STRENGTH -> strength
        Attribute.ENDURANCE -> endurance
        Attribute.AGILITY -> agility
        Attribute.VITALITY -> vitality
    }

    fun plus(attribute: Attribute, amount: Int): AttributeScores = when (attribute) {
        Attribute.STRENGTH -> copy(strength = strength + amount)
        Attribute.ENDURANCE -> copy(endurance = endurance + amount)
        Attribute.AGILITY -> copy(agility = agility + amount)
        Attribute.VITALITY -> copy(vitality = vitality + amount)
    }

    companion object {
        val ZERO = AttributeScores()
    }
}
