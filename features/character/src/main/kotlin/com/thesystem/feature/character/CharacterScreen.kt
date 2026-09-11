package com.thesystem.feature.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thesystem.core.designsystem.TheSystemColors
import com.thesystem.core.domain.Character

@Composable
fun CharacterScreen(character: Character, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = character.characterClass.displayName,
            color = TheSystemColors.Accent,
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = "Level ${character.level}",
            color = TheSystemColors.TextPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Unspent points: ${character.unspentPoints}",
            color = TheSystemColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
