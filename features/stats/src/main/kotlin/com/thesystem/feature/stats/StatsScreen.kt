package com.thesystem.feature.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thesystem.core.designsystem.TheSystemColors
import com.thesystem.core.designsystem.component.StatBar
import com.thesystem.core.designsystem.tint
import com.thesystem.core.domain.Attribute
import com.thesystem.core.domain.Character
import com.thesystem.core.domain.LevelCurve

@Composable
fun StatsScreen(character: Character, modifier: Modifier = Modifier) {
    val (current, required) = LevelCurve.progressToNextLevel(character.totalXp)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Attribute.entries.forEach { attribute ->
            StatBar(
                title = attribute.displayName,
                value = character.allocated[attribute],
                tint = attribute.tint,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Text(
            text = "Level ${character.level} · $current/$required XP",
            color = TheSystemColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
