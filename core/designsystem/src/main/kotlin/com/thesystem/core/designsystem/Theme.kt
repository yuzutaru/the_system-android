package com.thesystem.core.designsystem

import androidx.compose.ui.graphics.Color
import com.thesystem.core.domain.Attribute

object TheSystemColors {
    val Background = Color(0xFF0D0F14)
    val Surface = Color(0xFF1C1F26)
    val Accent = Color(0xFFDEA83E)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0x9EFFFFFF)
    val Danger = Color(0xFFD94D4D)
}

val Attribute.tint: Color
    get() = when (this) {
        Attribute.STRENGTH -> Color(0xFFDB4D4D)
        Attribute.ENDURANCE -> Color(0xFF4DB873)
        Attribute.AGILITY -> Color(0xFF599EDE)
        Attribute.VITALITY -> Color(0xFFCC9F3F)
    }
