package com.thesystem.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thesystem.core.designsystem.TheSystemColors
import com.thesystem.core.designsystem.tint
import com.thesystem.core.domain.Attribute

@Composable
fun HomeScreen(
    onLogWorkout: () -> Unit,
    onSeeStats: () -> Unit,
    onBuildCharacter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TheSystemColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Hero(onLogWorkout = onLogWorkout, onSeeStats = onSeeStats)
        HowItWorks()
        BeginnerPaths()
        ClosingCta(onBuildCharacter = onBuildCharacter)
    }
}

@Composable
private fun Hero(onLogWorkout: () -> Unit, onSeeStats: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "FREE & OPEN-SOURCE FITNESS TRACKER",
            color = TheSystemColors.Accent,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Track workouts. Log your progress. See the gains.",
            color = TheSystemColors.TextPrimary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "The System is a fitness tracker for beginners. It tells you where to " +
                "start, logs every session, tracks how you improve, and turns your grind " +
                "into levels and stats along the way.",
            color = TheSystemColors.TextSecondary,
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onLogWorkout) {
                Text("Log a workout")
            }
            OutlinedButton(onClick = onSeeStats) {
                Text("See your stats")
            }
        }
    }
}

@Composable
private fun HowItWorks() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "How it works",
            color = TheSystemColors.TextPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Three habits, one loop: log it, track it, learn from it.",
            color = TheSystemColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        StepCard(
            number = 1,
            title = "Log your workout",
            body = "Record strength sets, cardio, or mobility. Every entry earns XP " +
                "and gold automatically.",
        )
        StepCard(
            number = 2,
            title = "Log your progression",
            body = "XP levels you up, estimated 1RMs track your strength, and streaks " +
                "keep your routine honest.",
        )
        StepCard(
            number = 3,
            title = "Analyze your progress",
            body = "Watch your STR, END, AGI, and VIT grow, and unlock a class that " +
                "reflects how you train.",
        )
    }
}

@Composable
private fun StepCard(number: Int, title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TheSystemColors.Surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "STEP $number",
                color = TheSystemColors.Accent,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = title,
                color = TheSystemColors.TextPrimary,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = body,
                color = TheSystemColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun BeginnerPaths() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "New here? Pick where to start.",
            color = TheSystemColors.TextPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Choose the path that matches your goal — The System grows the " +
                "matching stat as you train.",
            color = TheSystemColors.TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Attribute.entries.forEach { attribute ->
            PathCard(attribute = attribute)
        }
    }
}

@Composable
private fun PathCard(attribute: Attribute) {
    val (focus, first) = pathCopy(attribute)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TheSystemColors.Surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(attribute.tint),
                )
                Text(
                    text = attribute.displayName,
                    color = TheSystemColors.TextPrimary,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = attribute.code,
                    color = TheSystemColors.TextSecondary,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Text(
                text = focus,
                color = TheSystemColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "First workout: $first",
                color = TheSystemColors.TextSecondary,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ClosingCta(onBuildCharacter: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TheSystemColors.Surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Start your first session today.",
                color = TheSystemColors.TextPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "No account, no subscription. Free, open source, and offline-first.",
                color = TheSystemColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(onClick = onBuildCharacter) {
                Text("Build your character")
            }
        }
    }
}

private fun pathCopy(attribute: Attribute): Pair<String, String> = when (attribute) {
    Attribute.STRENGTH -> "Getting stronger with weights" to
        "Start with 3 sets of 5 on squats, bench, or rows."
    Attribute.ENDURANCE -> "Building stamina and heart health" to
        "Try a 20-minute easy run or bike, then add intervals."
    Attribute.AGILITY -> "Moving better, faster, and more freely" to
        "Begin with bodyweight squats, push-ups, and mobility drills."
    Attribute.VITALITY -> "Recovering well and staying consistent" to
        "Set a sleep target and log one mobility session this week."
}
